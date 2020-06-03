package com.example.info448project.activity

import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.api.geocoding.v5.GeocodingCriteria
import com.mapbox.api.geocoding.v5.MapboxGeocoding
import com.mapbox.api.geocoding.v5.models.CarmenFeature
import com.mapbox.api.geocoding.v5.models.GeocodingResponse
import com.mapbox.core.exceptions.ServicesException
import com.mapbox.geojson.Point
import com.example.info448project.R
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.Layer
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class LocationPickerActivity : AppCompatActivity(), PermissionsListener,
    OnMapReadyCallback {
    private var mapView: MapView? = null
    private var mapboxMap: MapboxMap? = null
    private var selectLocationButton: Button? = null
    private var permissionsManager: PermissionsManager? = null
    private var hoveringMarker: ImageView? = null
    private var droppedMarkerLayer: Layer? = null

    companion object {
        private const val DROPPED_MARKER_LAYER_ID = "DROPPED_MARKER_LAYER_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mapbox access token is configured here
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
        // This needs to be called after the access token is configured.
        setContentView(R.layout.location_picker)
        mapView = findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this@LocationPickerActivity.mapboxMap = mapboxMap
        mapboxMap.setStyle(
            Style.MAPBOX_STREETS
        ) { style ->
            enableLocationPlugin(style)
            // Toast instructing user to tap on the map
            Toast.makeText(
                this@LocationPickerActivity,
                getString(R.string.move_map_instruction), Toast.LENGTH_SHORT
            ).show()
            // When user is still picking a location, we hover a marker above the mapboxMap in the center.
            hoveringMarker = ImageView(this@LocationPickerActivity)
            hoveringMarker!!.setImageResource(R.drawable.red_marker)
            val params = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER
            )
            hoveringMarker!!.layoutParams = params
            mapView!!.addView(hoveringMarker)
            // Initialize, but don't show, a SymbolLayer for the marker icon which will represent a selected location.
            initDroppedMarker(style)
            // Button for user to drop marker or to pick marker back up.
            selectLocationButton = findViewById(R.id.select_location_button)
            selectLocationButton?.setOnClickListener(View.OnClickListener {
                if (hoveringMarker!!.visibility == View.VISIBLE) { // Use the map target's coordinates to make a reverse geocoding search
                    val mapTargetLatLng =
                        mapboxMap.cameraPosition.target
                    // Hide the hovering red hovering ImageView marker
                    hoveringMarker!!.visibility = View.INVISIBLE
                    // Transform the appearance of the button to become the cancel button
                    selectLocationButton?.setBackgroundColor(
                        ContextCompat.getColor(
                            this@LocationPickerActivity,
                            R.color.colorAccent
                        )
                    )
                    selectLocationButton?.setText(getString(R.string.location_picker_select_location_button_cancel))
                    // Show the SymbolLayer icon to represent the selected map location
                    if (style.getLayer(DROPPED_MARKER_LAYER_ID) != null) {
                        val source =
                            style.getSourceAs<GeoJsonSource>("dropped-marker-source-id")
                        source?.setGeoJson(
                            Point.fromLngLat(
                                mapTargetLatLng.longitude,
                                mapTargetLatLng.latitude
                            )
                        )
                        droppedMarkerLayer =
                            style.getLayer(DROPPED_MARKER_LAYER_ID)
                        if (droppedMarkerLayer != null) {
                            droppedMarkerLayer!!.setProperties(
                                PropertyFactory.visibility(
                                    Property.VISIBLE
                                )
                            )
                        }
                    }
                    // Use the map camera target's coordinates to make a reverse geocoding search
                    reverseGeocode(
                        Point.fromLngLat(
                            mapTargetLatLng.longitude,
                            mapTargetLatLng.latitude
                        )
                    )
                } else { // Switch the button appearance back to select a location.
                    selectLocationButton?.setBackgroundColor(
                        ContextCompat.getColor(
                            this@LocationPickerActivity,
                            R.color.colorPrimary
                        )
                    )
                    selectLocationButton?.setText(getString(R.string.location_picker_select_location_button_select))
                    // Show the red hovering ImageView marker
                    hoveringMarker!!.visibility = View.VISIBLE
                    // Hide the selected location SymbolLayer
                    droppedMarkerLayer =
                        style.getLayer(DROPPED_MARKER_LAYER_ID)
                    if (droppedMarkerLayer != null) {
                        droppedMarkerLayer!!.setProperties(
                            PropertyFactory.visibility(
                                Property.NONE
                            )
                        )
                    }
                }
            })
        }
    }

    private fun initDroppedMarker(loadedMapStyle: Style) { // Add the marker image to map
        loadedMapStyle.addImage(
            "dropped-icon-image", (ResourcesCompat.getDrawable(this.resources, R.drawable.blue_marker, null) as VectorDrawable).toBitmap()
        )
        loadedMapStyle.addSource(GeoJsonSource("dropped-marker-source-id"))
        loadedMapStyle.addLayer(
            SymbolLayer(
                DROPPED_MARKER_LAYER_ID,
                "dropped-marker-source-id"
            ).withProperties(
                PropertyFactory.iconImage("dropped-icon-image"),
                PropertyFactory.visibility(Property.NONE),
                PropertyFactory.iconAllowOverlap(true),
                PropertyFactory.iconIgnorePlacement(true)
            )
        )
    }

    public override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView!!.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView!!.onStop()
    }

    public override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        permissionsManager!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onExplanationNeeded(permissionsToExplain: List<String>) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG)
            .show()
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted && mapboxMap != null) {
            val style = mapboxMap!!.style
            style?.let { enableLocationPlugin(it) }
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG)
                .show()
            finish()
        }
    }

    /**
     * This method is used to reverse geocode where the user has dropped the marker.
     *
     * @param point The location to use for the search
     */
    private fun reverseGeocode(point: Point) {
        try {
            val client: MapboxGeocoding = MapboxGeocoding.builder()
                .accessToken(getString(R.string.mapbox_access_token))
                .query(Point.fromLngLat(point.longitude(), point.latitude()))
                .geocodingTypes(GeocodingCriteria.TYPE_ADDRESS)
                .build()
            client.enqueueCall(object : Callback<GeocodingResponse?> {
                override fun onResponse(
                    call: Call<GeocodingResponse?>?,
                    response: Response<GeocodingResponse?>
                ) {
                    if (response.body() != null) {
                        val results: List<CarmenFeature> =
                            response.body()!!.features()
                        if (results.isNotEmpty()) {
                            val feature: CarmenFeature = results[0]
                            // If the geocoder returns a result, we take the first in the list and show a Toast with the place name.
                            mapboxMap!!.getStyle { style ->
                                if (style.getLayer(DROPPED_MARKER_LAYER_ID) != null) {
                                    Toast.makeText(
                                        this@LocationPickerActivity,
                                        java.lang.String.format(
                                            getString(R.string.location_picker_place_name_result),
                                            feature.placeName()
                                        ), Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                this@LocationPickerActivity,
                                getString(R.string.location_picker_dropped_marker_snippet_no_results),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onFailure(
                    call: Call<GeocodingResponse?>?,
                    throwable: Throwable
                ) {
                    Timber.e("Geocoding Failure: %s", throwable.message)
                }
            })
        } catch (servicesException: ServicesException) {
            Timber.e("Error geocoding: %s", servicesException.toString())
            servicesException.printStackTrace()
        }
    }

    private fun enableLocationPlugin(loadedMapStyle: Style) { // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) { // Get an instance of the component. Adding in LocationComponentOptions is also an optional
// parameter
            val locationComponent = mapboxMap!!.locationComponent
            locationComponent.activateLocationComponent(
                LocationComponentActivationOptions.builder(
                    this, loadedMapStyle
                ).build()
            )
            locationComponent.isLocationComponentEnabled = true
            // Set the component's camera mode
            locationComponent.cameraMode = CameraMode.TRACKING
            locationComponent.renderMode = RenderMode.NORMAL
        } else {
            permissionsManager = PermissionsManager(this)
            permissionsManager!!.requestLocationPermissions(this)
        }
    }
}

package com.example.info448project.activity.dummy

import android.content.res.Resources
import com.example.info448project.R
import com.example.info448project.ProjectApp
import java.util.ArrayList
import java.util.HashMap

object DummyContent {

    val ITEMS: MutableList<DummyItem> = ArrayList()
    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    init {
        // Add 3 sample items.
        addItem(DummyItem("1", "Food Safety",
            "Food Safety", R.drawable.food, "You should always handle and prepare food safely, including keeping raw meat seperate from other foods, refrigerating perishable foods, and cooking meat to the right temperature to kill harmful bacteria. In general, because of the poor survivability of coronaviruses on surfaces, there is likely very low risk of spread from food products or packaging.", "Tip 1 - Preparing Food Properly", R.mipmap.food_full_foreground))
        addItem(DummyItem("2", "Common Misconceptions",
            "Common Misconceptions", R.drawable.miscon, "description", "Tip 2 - Identifying False Info", R.drawable.food_full_background))
        addItem(DummyItem("3", "Community Support",
            "Community Support", R.drawable.community, "description", "Tip 3 - Finding Community Support", R.drawable.food_full_background))
        addItem(DummyItem("4", "Personal Well-being",
            "Personal Well-being", R.drawable.personal, "description", "Tip 4 - Maintaining Well-being", R.drawable.food_full_background))
        addItem(DummyItem("5", "Working at Home",
            "Working at Home", R.drawable.home, "", "Tip 5 - Adjusting to Home Work", R.drawable.food_full_background))
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    /**
     * A dummy item representing a single health tip.
     */
    data class DummyItem(val id: String, val content: String, val details: String, val icon: Int, val description: String, val subheading: String, val background: Int) {
        override fun toString(): String = content
    }
}

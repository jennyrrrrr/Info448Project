package com.example.info448project.activity.dummy

import com.example.info448project.R
import java.util.ArrayList
import java.util.HashMap

object DummyContent {

    val ITEMS: MutableList<DummyItem> = ArrayList()
    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    init {
        addItem(DummyItem("1", "Food Safety",
            "Food Safety", R.drawable.food, "You should always handle and prepare food safely, including keeping raw meat seperate from other foods, refrigerating perishable foods, and cooking meat to the right temperature to kill harmful bacteria. In general, because of the poor survivability of coronaviruses on surfaces, there is likely very low risk of spread from food products or packaging.\n\n", "Tip 1 - Preparing Food Properly", R.mipmap.food_full_foreground))
        addItem(DummyItem("2", "Common Misconceptions",
            "Common Misconceptions", R.drawable.miscon, "There are currently no drugs licensed for the treatment or prevention of COVID-19\n\nWhile several drug trials are ongoing, there is currently no proof that hydroxychloroquine or any other drug can cure or prevent COVID-19. The misuse of hydroxychloroquine can cause serious side effects and illness and even lead to death. WHO is coordinating efforts to develop and evaluate medicines to treat COVID-19.\n\nDrinking methanol, ethanol or bleach DOES NOT prevent or cure COVID-19 and can be extremely dangerous\n\nThey will not kill the virus in your body and they will harm your internal organs.To protect yourself against COVID-19, disinfect objects and surfaces, especially the ones you touch regularly.\n\n", "Tip 2 - Identifying False Info", R.mipmap.miscon_full_foreground))
        addItem(DummyItem("3", "Community Support",
            "Community Support", R.drawable.community, "Below you can find some ways to support our larger community while recognizing the need for social distancing\n\nCheck-in on your neighbors, especially those who are older\n\nDonate blood to help maintain a sufficient blood supply and prevent shortages as concerns about the outbreak of COVID-19 rise in the U.S\n\nConsider donating to a mutual aid fund.\n\nSupport local small businesses who have experienced a significant decrease in patronage\n\nDonate funds to community partners who provide critical support to our neighbors\n\n", "Tip 3 - Finding Community Support", R.mipmap.community_full_foreground))
        addItem(DummyItem("4", "Personal Well-being",
            "Personal Well-being", R.drawable.personal, "Stay physically safe from the virus - " +
                    "In this case, the biggest safety behaviors (physical distancing and hand washing) which decrease transmission of the COVID-19 virus, are also an integral part of anxiety management.Stay home when you can.\n\nLimit media to reduce anxiety - Watching or scrolling through the media makes us even more anxious. An excess of news and visual images about a traumatic event can create symptoms of post-traumatic stress disorder\n\nGet and provide warm, comforting, social support by video, phone, or text - This is critical! Taking time to share your feelings and to listen and support others will go a long way.\n\n", "Tip 4 - Maintaining Well-being", R.mipmap.personal_full_foreground))
        addItem(DummyItem("5", "Working at Home",
            "Working at Home", R.drawable.home, "As social distancing and stay-at-home orders are extended throughout the country, many students and workers are coming to terms with the fact that we will be working from home and learning remotely for quite some time. For most of us, the responsibilities of school and work continue, and we are trying to stay on track as best as we can. Below are some simple tips for staying motivated during this time.\n\nSet small daily goals - Setting daily goals can help you to make your day as productive as possible, but be realistic with the goals you set.\n\nCarve out time in your day for work and for relaxation - Scheduling when you will work and when you will relax can help you to manage a healthy work-life balance at home. For some, a conventional 9 to 5 schedule with a lunch break in the middle helps them to stay focused.\n\n", "Tip 5 - Adjusting to Home Work", R.mipmap.home_full_foreground))
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

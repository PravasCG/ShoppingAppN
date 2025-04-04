package com.shoppy.botpassignment.models

data class MSliders(
    var slider_image: Any? = null
) {

    fun convertToMap(): MutableMap<String, Any?>{

        return mutableMapOf(
            "slider_image" to this.slider_image
        )

    }
}
package com.shoppy.botpassignment.data

//data class SuccessOrError<Boolean,E: String>(
////    var data: T? = null,
//    var isSuccess: Boolean,
//    var error: E
//)

data class SuccessOrError(
    val isSuccess: Boolean = false,
    val error: String? = null
)
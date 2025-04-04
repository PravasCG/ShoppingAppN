package com.shoppy.botpassignment.network

import com.shoppy.botpassignment.ShoppingAppUtils
import com.shoppy.botpassignment.models.PushNotificationData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApi {

//    @Singleton
    @Headers("Authorization: key = ${ShoppingAppUtils.SERVER_KEY}", "Content-Type: ${ShoppingAppUtils.CONTENT_TYPE}")
    @POST("fcm/send")
    suspend fun postNotification( @Body notification: PushNotificationData): Response<ResponseBody>
}
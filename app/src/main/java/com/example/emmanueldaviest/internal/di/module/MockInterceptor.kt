package com.example.emmanueldaviest.internal.di.module

import android.app.Application
import okhttp3.*
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

/**
 * This will help to test our networking code while the API is not implemented
 * yet at the Backend side.
 */
class MockInterceptor(var  application: Application) : Interceptor {


    fun getJsonString(application: Application): String {

        var jsonString: String? = null
        try {
            var inputStream: InputStream = application.assets.open("data.json")

            var size = inputStream.available()
            var buffer = ByteArray(size)

            inputStream.read(buffer)
            inputStream.close()
            jsonString = String(buffer, Charset.forName("UTF-8"))

        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        return jsonString ?: jsonString!!; " "
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val SUCCESS_CODE = 200

//        if (BuildConfig.DEBUG) {
        val uri = chain.request().url().uri().toString()
        val responseString = getJsonString(application)


     var response =  chain.proceed(chain.request())
            .newBuilder()
            .code(SUCCESS_CODE)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(
                ResponseBody.create(
                    MediaType.parse("application/json"),
                    responseString.toByteArray()
                )
            )
            .addHeader("content-type", "application/json")
            .build()

        return  response
    }

}
package com.demo

import com.demo.model.photos.ItemPhoto
import com.demo.networking.ApiInterface
import com.demo.networking.HttpStatusCode
import com.demo.networking.Repository
import org.junit.Test

import org.junit.Assert.*
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    var apiInterface : ApiInterface ?= null

//    val bookingResponse = apiInterface!!.getPhotos()
//        .extractBodyAs(ItemPhoto::class.java)

    @Test
    fun `get photos`() {
//        val bookingId = apiInterface.getPhotos()
//        bookingResponse.getPhotos()
//            .assertStatusCodeAndContentType(statusCode = HttpStatusCode.ACCEPT, contentType = "text")
    }
}
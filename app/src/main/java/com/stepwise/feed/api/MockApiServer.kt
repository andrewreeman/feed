package com.stepwise.feed.api

import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.HttpUrl
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.lang.Runnable
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

/**
 * this is a hacky way of simulating a real api as 'close to the network' as possible.
 * It isn't great as it contains a race condition for initialising the base url
 */

class MockApiServer {
    var baseUrl: HttpUrl? = null
    private var server = MockWebServer()
    private var whenReadyCallback: Runnable? = null

    private val backgroundScope = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())

    init {
        server.dispatcher = RequestHandler()
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    fun start() {
        backgroundScope.launch {
            withContext(Dispatchers.IO) {
                delay(500)
                server.start()
                baseUrl = server.url("/")
            }
        }
    }

    fun onReady(callback: () -> Unit) {
        if(baseUrl != null) {
            callback()
        }
        else {
            backgroundScope.launch {
                withContext(Dispatchers.IO) {
                    var i = 0
                    while(baseUrl == null && i < 100) {
                        i++
                        delay(100)
                    }

                    callback()
                }
            }

        }
    }

    class RequestHandler: Dispatcher() {
        private val gson = Gson()
        private var data = ArrayList<ContentApiModel>()

        init {
            for(i in 0..5) {
                data.add(ContentApiModel("Title $i", "Description $i"))
            }
        }

        override fun dispatch(request: RecordedRequest): MockResponse {
            return when(request.method?.toLowerCase(Locale.ROOT)) {
                "get" -> MockResponse().setBody(gson.toJson(data))
                "post" -> createNewData(request)
                else  -> MockResponse().setHttp2ErrorCode(404)
            }
        }

        private fun createNewData(request: RecordedRequest): MockResponse {
            val body = request.body.readUtf8()
            val newContent = gson.fromJson(body, ContentApiModel::class.java)

            data.add(newContent)
            return MockResponse().setBody(gson.toJson(newContent))
        }


    }
}
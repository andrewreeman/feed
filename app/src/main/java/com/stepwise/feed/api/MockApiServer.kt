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
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

/**
 * this is a hacky way of simulating a real api as 'close to the network' as possible.
 * It isn't great as it contains a race condition for initialising the base url
 */

class MockApiServer {
    var baseUrl: HttpUrl? = null
    private var server = MockWebServer()

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
                data.add(newModel(i))
            }
        }

        override fun dispatch(request: RecordedRequest): MockResponse {
            return when(request.method?.toLowerCase(Locale.ROOT)) {
                "get" -> loadData().setBodyDelay(3, TimeUnit.SECONDS)
                "post" -> createNewData(request).setBodyDelay(2, TimeUnit.SECONDS)
                else  -> MockResponse().setHttp2ErrorCode(404)
            }
        }

        private fun createNewData(request: RecordedRequest): MockResponse {
            val body = request.body.readUtf8()
            var newContent = gson.fromJson(body, ContentApiModel::class.java)

            newContent = ContentApiModel(data.size, newContent.title, newContent.description)
            data.add(newContent)
            return MockResponse().setBody(gson.toJson(newContent))
        }

        private fun loadData(): MockResponse {
            val size = data.size
            for(i in size..size+3) {
                data.add(newModel(i))
            }

            return MockResponse().setBody(gson.toJson(data))
        }

        private fun newModel(index: Int): ContentApiModel {
            return ContentApiModel(index, "Title $index", "Description $index")
        }

    }
}
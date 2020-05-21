package com.stepwise.feed.api

import android.content.res.Resources
import com.google.gson.Gson
import com.stepwise.feed.R
import kotlinx.coroutines.*
import okhttp3.HttpUrl
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.lang.Runnable
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.stream.Stream
import java.util.stream.Stream.empty
import kotlin.collections.ArrayList

/**
 * this is a hacky way of simulating a real api as 'close to the network' as possible.
 * It isn't great as it contains a race condition for initialising the base url
 */

class MockApiServer {
    var baseUrl: HttpUrl? = null

    private var server = MockWebServer()
    private val dispatcher = RequestHandler()
    private val backgroundScope = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())

    init {
        server.dispatcher = dispatcher
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    fun start(resources: Resources) {
        backgroundScope.launch {
            withContext(Dispatchers.IO) {
                delay(500)
                server.start()
                dispatcher.readDataFromResources(resources)
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
        private var customData = ArrayList<ContentApiModel>()
        private var dataIterator = data.iterator()

        override fun dispatch(request: RecordedRequest): MockResponse {
            return when(request.method?.toLowerCase(Locale.ROOT)) {
                "get" -> loadData().setBodyDelay(3, TimeUnit.SECONDS)
                "post" -> createNewData(request).setBodyDelay(2, TimeUnit.SECONDS)
                else  -> MockResponse().setHttp2ErrorCode(404)
            }
        }

        fun readDataFromResources(resources: Resources) {
            val reader = BufferedReader(InputStreamReader(resources.openRawResource(R.raw.quotes)))
            val quoteData = reader.readText()

            val quoteArray = gson.fromJson(quoteData, Array<Quote>::class.java)
                .toMutableList()
                .shuffled()

            quoteArray.forEachIndexed { index, quote ->
                data.add(ContentApiModel(index, quote.author ?: "Unknown", quote.text))
            }

            dataIterator = data.iterator()
        }

        private fun createNewData(request: RecordedRequest): MockResponse {
            val body = request.body.readUtf8()
            var newContent = gson.fromJson(body, ContentApiModel::class.java)

            newContent = ContentApiModel(data.size + customData.size, newContent.title, newContent.description)
            customData.add(newContent)
            return MockResponse().setBody(gson.toJson(newContent))
        }

        private fun loadData(): MockResponse {
            val response = ArrayList<ContentApiModel>()

            var i = 0
            while (dataIterator.hasNext() && i<3) {
                response.add(dataIterator.next())
                ++i
            }

            return MockResponse().setBody(gson.toJson(response))
        }

        private fun newModel(index: Int): ContentApiModel {
            return ContentApiModel(index, "Title $index", "Description $index")
        }
    }

    private data class Quote(val author: String?, val text: String)
}
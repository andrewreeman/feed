package com.stepwise.feed.api

import kotlinx.coroutines.*
import okhttp3.HttpUrl
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.lang.Runnable
import java.util.concurrent.Executors

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
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setBody("[{\"title\": \"A mock title\", \"description\": \"A mock description\"}]")
        }

    }
}
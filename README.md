# Quotes

This app displays quotes from authors from a mock server. The quotes are displayed in a list along with a pull down to fresh ability.
Pressing the floating action button allows the user to add a quote to this list.

# Architecture

The general architecture follows the MVP pattern. See the ui.mainpage.MainPageMVP interfaces file.
The model communicates with the repository which is implemented as a network communicating to a mock server.
The presenter maps data to and from the model to be displayed by the view.
The view uses a two fragments. One for displaying the list and another for adding an item.

Dagger is used to wire up the dependency graph.
Retrofit is used to provide the api.
Mockito is used for unit test mocking. Although a better mocking library would be is to use Mockk for Kotlin unit tests.
Mockk seems more capable of mocking coroutines and mocking Kotlin's data classes.

# Issues and future improvements

## Mock server
We are using a mock server which introduced architectural issues. The mock server (provided by OkHttp) needs to be launched async
before providing a url. When the app starts the api expects a url to be available however due to the async server launch this is not the case.

In the `loadContentWhenInitialized` method in the MainPageActivity there is a inelegant workaround for this that essentially waits for the presenter to be initialized before reloading the data from the api.
This is because the presenter is only injected after the server url is available. A better solution to this would either be:
1. Use a real api server
2. Use many async providers in the dependency graph. A good use case for RxJava perhaps.

## Paging
At the moment there is no paging solution. A good solution to this would be use Android's own Paging library

## Persistence
Along with persisting user additions via posting to a real api we should have persistence via caching local data for quicker access.  
The data access could then follow the route: check in-memory -> then check cache -> then check api.
We could even pipeline the process. So as data is paged in from memory we then grab the next page from the cache and then start filling the cache via the next page
from the api.










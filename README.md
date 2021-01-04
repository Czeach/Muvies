# Muvies
This is a native android application written in Kotlin. It is a movies app which displays movie data based on sorting criteria and extensive details of each movie and tv show. It fetches data from [The Movie DataBase API](https://www.themoviedb.org/) and is implemented using the MVVM pattern, Retrofit2 as a REST-Client, LiveData, ViewModel, Coroutines, Navigation Components and other android libraries.

## Libraries used
* [Retrofit2](https://square.github.io/retrofit/) a REST Client library (Helper Library) used in Android and Java to create an HTTP request and also to process the HTTP response from a REST API. 
* [ViewModel](https://developer.android.com/reference/android/arch/lifecycle/ViewModel#:~:text=Application%20context%20aware%20ViewModel%20.,calling%20the%20business%20logic%20classes).) a class that is responsible for preparing and managing the data for an Activity or a Fragment.
* [Kotlin coroutines](https://developer.android.com/kotlin/coroutines) a concurrency design pattern that you can use on Android to simplify code that executes asynchronously. On Android, coroutines help to manage long-running tasks that might otherwise block the main thread and cause your app to become unresponsive.
* [Android navigation component](https://developer.android.com/guide/navigation) to make navigations between fragments and passing of data between destinations easier.
* [Paging](https://developer.android.com/topic/libraries/architecture/paging) to  load and display small chunks of data at a time.
* [Glide](https://github.com/bumptech/glide) a fast and efficient open source media management and image loading framework for Android.

## Installation
Muvies requires a minimum API level of 23. Clone the repository. You will need an API key from [TMDB](https://www.themoviedb.org/) to request data. You'll need to create an account one in order to request an API Key. After you've gotten your API key, in your project's root directory, open the ```local.properties``` file and include the following line: ``` api_key = "YOUR API KEY" ```. You can now build the project.

## LICENSE
``` 
MIT LICENSE
Copyright (c) 2020 Ezichi Amarachi

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation 
files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, 
merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to 
do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO 
THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```




Coroutine are like light weight threads 

In kotlin, you do asynchronous programming using Suspend function.  

Suspend : Suspend is our indication that this function is a asynchronous, it won't return immediatly it will suspend for a while and then it will return the actual result later on. 

Coroutine builder 
Lanuch : it creates coroutine, it works on self in the background, just like starting background thread. what ever the logic inside curl braces will be working in some background thread pool. 

Every Lanuch comes with context, this is because you may have to tell where you want to return back once background task is done. hence every Lanuch has corresponding context . 

lanuch(UI) { // This make sure that the execuation is dispatch on its UI thread.

}

Lanuch is reqular function, it doesn't take suspend modifier on it. meaning that it doesn't wait for anything, it returns right away and it return with object called Job 

Lanuch signature 

```kotlin
lanuch (context : coroutineContext = defultdispatcher,
        block : Suspend() -> unit
        ) : Job {..}
```
        
By the way, kotlin suspending functions are designed to imitate sequential behavior by default 
        
Async / Await() 

Kotlin async function 

```kotlin
fun loadImageAsync(name : String) : Deferred<Image> = async {...}
 
 val deferred1 = loadImageAsync(name1)
 val deferred2 = loadImageAsync(name2)
 
 val image1 = deferred1.await()
 val image2 = deferred2.await()
 
 val result = combineImages (image1, image2)
 ```
 
 




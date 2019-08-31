Let me start off with basic elements of thread, cuncurrency before I start with coroutine.
 
We all know that Synchronous basically means that you can only execute one thing at a time. where as Asynchronous means that you can execute multiple things at a time and you don't have to finish executing the current thing in order to move on to next one.
 
We also know that Concurrency or Parallelism are two different thing. in short, parallelism is about doing lot of things at once, so that we can speed up our execution. in java to enable Parallelism we uses concept of thread, thread pool - executor service. 

Concurrency is nothing but dealing with lot of things at once, an ideal example to map this with real world problem is ticket reservation system, where multiple request suppose to be serve one at time although request are triggered simultaniously. in java concurrency can be acheived by using locks OR synchronized key. Anyway i dont dive this deep here . in short both Concurrency or Parallelism are made for Asynchronous.

To acheive Asynchronous, we create threads in a process. now what is this new thing called Coroutines, how does this varies from classic thread, is that some again thread ? what makes this difference. 
 
Before I tell you what is Coroutines and how does it work and other details? 
Let's assume you are creating client server interactive application, in which you need to make few network call to fetech some of the data, on receive data you have things to do like parse data and save it locally for later useage. when providing such facility in your app, obviously you organise things to be Asynchronous, creating multiple worker threads only the wat to achieve Asynchronous. what happens is, when you trigger network call, each of those call will take some time to respond back with data, response could be immeadiate Or it may take few hundred milli seonds OR even longer, during this interval your thread will be waiting state and it does nothing. 

So a thread can block, can be waiting state without doing anything for shorter interval of time when its live. there are many ways where thread go in blocking state. 

Consider situation where your application create more than 20 threads only for server client interaction and all of those are waiting for few seconds to receive network data. if these number of thread are waiting and not doing anything then that is inefficent use of CPU, because your CPU is ideal for those seconds while all the threads are waiting for network request to complete . 

we also have to consider below fact into account, 
- creation of a thread requires some time, thus the actual job does not start as soon as the request comes in. The client may notice a slight delay.
- Threads consume system resources (each thread consumes 1MB of memory in JVM, memory etc.), thus the system may run out of resources in case there is an unprecedented flow of client requests.

what's the core problem ? 
waiting threads don't allow scalling, threads are expensive, task waiting for IO blocks the thread itself. 

To overcome this problem, there are many framework created these days, one such is reactive programming (RxJava), Java fibers. it will help us uisng your cpu efficently and only using limited number of threads. 


 What we ideally got from these framworks is a concept of where we have very light weight threads which do not consumes lot of memory. the way it works is , when you submitted particular task to this light weight thread, thread will be executing until it reaches any blocking operation like IO operation or network operation or database operation. On its block, light weight thread will be unmount task from the tread, instead of keeping it in wait, it simple take out task from the thread. while taking out, it will save the current state of the task. any local variables and stack of the task saved sepeartly during this unmount. in this way light weighted thread will be avaialble to up other task once it is blocked. task will be mounted back to thread when it is done with operation, by that time task will be not assigned to same thread which is unmounted, it may chose to any other available thread to perform. These task is called coroutines OR in java it is called java fibers
 
 Task is called Coroutines here. 
 
 Coroutines are light-weight threads. They are launched with launch coroutine builder in a context of some CoroutineScope.


In java, to block main thread, you say 
Thread.sleep(2000L) // block main thread for 2 seconds

In kotlin you write that as 
runBlocking {         // but this expression blocks the main thread
        delay(2000L)  // ... while we delay for 2 seconds to keep JVM alive
} 
 
 
```kotlin





fun main() {
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // print after delay
    }
    println("Hello,") // main thread continues while coroutine is delayed
    Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive
}

fun main() { 
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1000L)
        println("World!")
    }
    println("Hello,") // main thread continues here immediately
    runBlocking {     // but this expression blocks the main thread
        delay(2000L)  // ... while we delay for 2 seconds to keep JVM alive
    } 
}

```


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
 
 




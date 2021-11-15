package com.ankesh.learning.executorservice;

import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /*
         Question - > How we should decide on number of threads to be created in thread pool
         Answer ->    It depends on the type of operation that we are trying to perform
         for example ->    1.  If your task is CPU intensive i.e task is going to consume cpus extensively
                                then max thread that can run at a time will be equal to total number of
                                 CPUs present at that time on machine. So the number of threads that we should create should be
                                equal to total no of cpus available.

                            2. IO intensive tasks
                                If your task is IO intensive for example database read or service call
                                then your thread may go into waiting state as response may take time in service call.

                                for example if we have 20 tasks that we want to run and we have 10 threads then
                                all 10 threads will go in waiting state SO it's not good because we have our CPUs
                                available and we can make use of that. SO in this case we should be creating more threads.



         Question -> Kinds of thread pool
         Answers  ->  1. fixedThreadPool  ->  LinknedBlocking queue is used
                      2. CachedThreadPool  -> uses synchronous queue which holds only 1 task at a time
                      3. ScheduledThreadPool ->  DelayedWork queue
                      4.SingleThreadedExecutor - > LinknedBlocking
         */


        //get count of available cores on machine
        System.out.println(Runtime.getRuntime().availableProcessors());

        /*
            The cached thread pool is useful for short-lived tasks
            in cached thread pool what happens is -> task is added to synchronous queue then this task finds if any thread is available,
                                                     if any thread is available then it assigns it..now next task comes in and it looks for other thread
                                                     hence if doesn't find it then it creates a new one.

                                                     for ex : - if there are around 100 tasks then theoritically it can create upto 99 threads but
                                                                in cachedThreadPool there is feature that if any thread is idle for more then 60 seconds(we can configure it)
                                                                then it gets killed.

             ExecutorService service = Executors.newCachedThreadPool();
         */

/*
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

            service.schedule(new MyThread(), 10, TimeUnit.SECONDS);     // runs the task after 10 seconds delay

            service.scheduleAtFixedRate(new MyThread(),15,10,TimeUnit.SECONDS); //task to run repeatedly after every 10 seconds

            service.scheduleWithFixedDelay(new MyThread(),15,10,TimeUnit.SECONDS); //task to run repeatedly 10 seconds after previous task completes

*/


/*
 SingleThreadExecutor =   Executors.newSingleThreadExecutor();

 creates only single thread. tasks are run sequentially.
 Still we use it because it does not block main thread

  */


        ExecutorService service = Executors.newFixedThreadPool(4);

//      Normal tasks using Runnable
//        for (int i =0; i<10; i++){
//            service.execute(new MyThread());
//        }
//        System.out.println("ThreadName" + Thread.currentThread().getName());


    //  Using Callable
        //Single task
//        Future<Integer> future = service.submit(new CallableDemo());

   //     After submitting tasks main thread continue to work
   //     when we do future.get() , then if the futures have the value from the tasks then it will return otherwise it will block the main thread
        // if any operation takes unexpected time, in those scenarios we can also configure timeout
//        future.get();

        //Multiple tasks submission
        List<Future<Integer>> futureList = new ArrayList<>();
        for(int i=0; i<10;i++){
            Future<Integer> future =   service.submit(new CallableDemo());
            futureList.add(future);
        }

        //retrieval
        for(int i=0; i<10;i++){
            futureList.get(i);
            System.out.println(futureList.get(i).get());
        }

        //As Future may block main thread if it does not have the value, then in this case we can use CompletableFuture
        CompletableFuture.supplyAsync(() -> new CallableDemo());

    }

}

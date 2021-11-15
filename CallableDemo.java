package com.ankesh.learning.executorservice;

import java.util.Random;
import java.util.concurrent.Callable;
//When we want to have  return value from task then we go for callable instead of Runnable
public class CallableDemo implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return new Random().nextInt();
    }
}

package com.ankesh.learning.executorservice;

import org.springframework.stereotype.Service;

@Service
public class Test {

    public static void main(String[] args) {
        Thread t = new Thread(new MyThread());
        t.start();

        System.out.println("Ankesh");
    }

}

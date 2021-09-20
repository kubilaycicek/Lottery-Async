package com.kubilaycicek.starter;

import com.kubilaycicek.service.LotteryAsyncService;

import java.util.concurrent.TimeUnit;

public class MainAsync {
    public static void main(String[] args) throws InterruptedException {

        var lotteryAsyncService = new LotteryAsyncService();
        System.err.println("Just started...");
        lotteryAsyncService.asyncDraw(60, 6)
                .thenAccept(numbers -> System.err.println(Thread.currentThread().getName() + " " + numbers));
        for (var i = 0; i < 10; i++) {
            System.err.println(Thread.currentThread().getName() + " - i:" + i);
        }
        TimeUnit.SECONDS.sleep(10);
    }
}

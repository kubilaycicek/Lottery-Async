package com.kubilaycicek.starter;

import com.kubilaycicek.service.LotteryService;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        var lotteryService = new LotteryService();
        System.err.println("Just started...");
        var numbers = lotteryService.draw(60, 6);
        System.err.println(numbers);
    }
}

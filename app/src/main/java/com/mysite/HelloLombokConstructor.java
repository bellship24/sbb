package com.mysite;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class HelloLombokConstructor {
    private final String hello;
    private final int lombok;

    public static void main(String[] args) {
        HelloLombokConstructor helloLombokConstructor = new HelloLombokConstructor("Hello", 5);
        System.out.println(helloLombokConstructor.getHello());
        System.out.println(helloLombokConstructor.getLombok());
    }
}

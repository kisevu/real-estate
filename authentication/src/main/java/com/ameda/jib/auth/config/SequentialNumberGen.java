package com.ameda.jib.auth.config;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: kev.Ameda
 */

@Component
public class SequentialNumberGen {

    private final AtomicInteger counter = new AtomicInteger(0);

    public String getNextFormatted(String prefix) {
        return String.format("%s-%04d", prefix, counter.incrementAndGet());
    }
}
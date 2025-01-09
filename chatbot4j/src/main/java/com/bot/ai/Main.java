package com.bot.ai;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

/**
 * Added some comments
 */

@NullMarked
public class Main {
    public static void main(String[] args) {
                System.out.println("Hello world!");

                new Main().notCovered(null);
    }


    /**
     *
     */

    void notCovered(@NonNull String checkNull) {
        System.out.println(checkNull.length());    }
}
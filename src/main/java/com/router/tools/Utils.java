package com.router.tools;

import java.util.concurrent.TimeUnit;

public class Utils {
    public static long timeInSec(long nanotime) {
        return TimeUnit.SECONDS.convert(nanotime, TimeUnit.NANOSECONDS);
    }
}

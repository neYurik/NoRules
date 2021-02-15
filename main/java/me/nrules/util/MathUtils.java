package me.nrules.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd2 = new BigDecimal(value);
        bd2 = bd2.setScale(places, RoundingMode.HALF_UP);
        return bd2.doubleValue();
    }
    public static double map(double val, double mx, double from, double to) {
        return Math.min(Math.max(from + (val / mx) * (to - from), from), to);
    }

    public static double square(double motionX) {
        motionX *= motionX;
        return motionX;
    }


}

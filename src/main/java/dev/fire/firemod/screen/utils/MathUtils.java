package dev.fire.firemod.screen.utils;

public class MathUtils {
    public static double lerp(double a, double b, double f) {
        return a * (1.0 - f) + (b * f);
    }
}

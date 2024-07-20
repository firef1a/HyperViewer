package dev.fire.firemod.devutils;

import net.minecraft.util.math.Vec3d;

public class MathUtils {
    public static double lerp(double a, double b, double f) {
        return a * (1.0 - f) + (b * f);
    }
    public static Vec3d align(Vec3d vec) {
        return new Vec3d(Math.floor(vec.x), Math.floor(vec.y), Math.floor(vec.z));
    }
}

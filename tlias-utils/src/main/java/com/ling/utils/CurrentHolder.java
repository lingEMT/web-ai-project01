package com.ling.utils;

public class CurrentHolder {

    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void setCurrent(Integer current) {
        threadLocal.set(current);
    }

    public static Integer getCurrent() {
        return threadLocal.get();
    }

    public static void removeCurrent() {
        threadLocal.remove();
    }
}

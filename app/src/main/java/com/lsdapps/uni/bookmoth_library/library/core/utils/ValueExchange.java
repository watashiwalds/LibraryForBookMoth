package com.lsdapps.uni.bookmoth_library.library.core.utils;

public class ValueExchange {
    public static int transparencyHexToPercent(int argbInt) {
        int apart = (argbInt >> 24) & 0xFF;
        return (int)((1 - (apart/255f))*100);
    }
    public static String makeTransparencyParseColorValue(int transparencyPercent, int color) {
        int alpha = Math.max(0, Math.min(255, (int)(255-(transparencyPercent/100.0)*255)));

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        return String.format("#%02X%02X%02X%02X", alpha, red, green, blue);
    }
}

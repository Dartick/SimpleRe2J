package org.xiaoheshan.simplere2j.utils;

/**
 * @author _Chf
 * @date 2017-09-20
 */
public final class ArrayUtil {

    private ArrayUtil() {
    }

    public static Character[] makeCharArray(String input) {
        char[] chars = input.toCharArray();
        return makeCharArray(chars);
    }

    public static Character[] makeCharArray(char[] chars) {
        Character[] result = new Character[chars.length];
        for (int i = 0; i < chars.length; i++) {
            result[i] = chars[i];
        }
        return result;
    }

    public static char[] makeCharArray(Character[] chars) {
        char[] result = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            result[i] = chars[i];
        }
        return result;
    }

    public static int[] makeIntArray(Integer[] integers) {
        int[] result = new int[integers.length];
        for (int i = 0; i < integers.length; i++) {
            result[i] = integers[i];
        }
        return result;
    }

    public static int[] makeIntArray(char[] chars) {
        int[] result = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            result[i] = chars[i];
        }
        return result;
    }

    /*public static Character[] supportChars() {
        Character[] result = new Character[1 << 16];
        int index = 0;
        for (char i = Character.MIN_VALUE; i < Character.MAX_VALUE; i++) {
            result[index++] = i;
        }
        return result;
    }*/

    public static Integer[] supportChars() {
        Integer[] result = new Integer[1 << 16];
        int index = 0;
        for (int i = Character.MIN_VALUE; i < Character.MAX_VALUE; i++) {
            result[index++] = i;
        }
        return result;
    }

}

package org.xiaoheshan.simplere2j.utils;

import org.xiaoheshan.simplere2j.parser.handler.CaseHandlers;

/**
 * @author _Chf
 * @date 2017-09-25
 */
public final class CharUtil {

    private CharUtil() {
    }

    public static boolean isLiteral(char ch) {
        return !CaseHandlers.getSupportOperationChars().contains(ch);
    }
}

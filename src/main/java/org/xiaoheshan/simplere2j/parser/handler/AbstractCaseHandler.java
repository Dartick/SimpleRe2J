package org.xiaoheshan.simplere2j.parser.handler;

/**
 * @author _Chf
 * @date 2017-09-20
 */
public abstract class AbstractCaseHandler implements CaseHandler {

    @Override
    public boolean matched(char ch) {
        for (char c : matchedCase()) {
            if (c == ch) {
                return true;
            }
        }
        return false;
    }

}

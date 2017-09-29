package org.xiaoheshan.simplere2j.ast;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public interface CaptureType {

    boolean is(String that);

    boolean isNameType();

    int getPrefixLength();
}

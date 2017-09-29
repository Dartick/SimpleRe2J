package org.xiaoheshan.simplere2j.matcher;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public interface InputAdapter {

    int get(int offset);

    int start();

    int end();

    void setStart(int start);

    void setEnd(int end);

    String getSubSequence(int from, int to);

    boolean isEnd(int offset);

}

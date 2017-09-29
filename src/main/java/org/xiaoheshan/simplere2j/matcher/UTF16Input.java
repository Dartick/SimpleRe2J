package org.xiaoheshan.simplere2j.matcher;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public class UTF16Input implements InputAdapter {

    private int start;
    private int end;
    private CharSequence charSequence;

    public UTF16Input(CharSequence charSequence) {
        this.charSequence = charSequence;
        this.start = 0;
        this.end = charSequence.length();
    }

    public UTF16Input(CharSequence charSequence, int start, int end) {
        this.charSequence = charSequence;
        this.start = start;
        this.end = end;
    }

    @Override
    public int get(int offset) {
        int index = start + offset;
        if (index < end) {
            return charSequence.charAt(index);
        }
        return 0;
    }

    @Override
    public int start() {
        return start;
    }

    @Override
    public int end() {
        return end;
    }

    @Override
    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String getSubSequence(int from, int to) {
        return charSequence.subSequence(from, to).toString();
    }

    @Override
    public boolean isEnd(int offset) {
        return start + offset >= end;
    }
}

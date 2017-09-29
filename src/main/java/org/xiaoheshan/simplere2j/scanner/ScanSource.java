package org.xiaoheshan.simplere2j.scanner;

/**
 * @author _Chf
 * @date 2017-09-20
 */
public class ScanSource {

    private String data;
    private int cursor;

    public ScanSource(String data) {
        this.data = data;
        this.cursor = 0;
    }

    public boolean hasNext() {
        return cursor + 1 <= data.length();
    }

    public boolean isEnd() {
        return cursor >= data.length();
    }

    public char peek() {
        if (isEnd()) {
            return 0;
        }
        return data.charAt(cursor);
    }

    public char poll() {
        return data.charAt(cursor++);
    }

    public char next() {
        return data.charAt(cursor+1);
    }

    public char get(int offset) {
        return data.charAt(cursor+offset);
    }

    public String sub(int length) {
        if (length == 0) {
            return "";
        }
        return sub(cursor, length);
    }

    public String sub(int from, int length) {
        return data.substring(from, from + length);
    }

    public void skip() {
        skip(1);
    }

    public void skip(int offset) {
        cursor += offset;
    }

    public void reset() {
        cursor = 0;
    }

}

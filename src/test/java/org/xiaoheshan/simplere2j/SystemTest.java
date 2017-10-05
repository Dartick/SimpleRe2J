package org.xiaoheshan.simplere2j;

import org.junit.Test;
import org.xiaoheshan.simplere2j.matcher.Matcher;
import org.xiaoheshan.simplere2j.pattern.Pattern;

/**
 * @author _Chf
 * @date 2017-10-02
 */
public class SystemTest {

    @Test
    public void test() throws Exception {

        String input = "<title>1234</title> <title>5678</title> <title>89</title>";

        String pattern = "<title>(.*?)</title>";

        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(input);
        System.out.println(matcher.matches());
        System.out.println(matcher.start() + " " + matcher.end());

        while (matcher.find()) {
            System.out.println(matcher.group());
        }

    }
}

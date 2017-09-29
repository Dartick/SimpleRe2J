package org.xiaoheshan.simplere2j.matcher.machine;

import org.junit.Test;
import org.xiaoheshan.simplere2j.matcher.Matcher;
import org.xiaoheshan.simplere2j.pattern.Pattern;

/**
 * @author _Chf
 * @date 2017-09-28
 */
public class VirtualMachineTest {

    @Test
    public void execute() throws Exception {

        String input = "abcd";

        String regex = "a(?<test>[b-c]{2})*d";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        System.out.println(matcher.matches());
        System.out.println(matcher.start() + " " + matcher.end());
        System.out.println(matcher.group(1));
        System.out.println(matcher.group("test"));

    }

}

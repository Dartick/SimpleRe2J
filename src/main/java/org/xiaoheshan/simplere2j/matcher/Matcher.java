package org.xiaoheshan.simplere2j.matcher;

import org.xiaoheshan.simplere2j.pattern.Pattern;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author _Chf
 * @date 2017-09-29
 */
public class Matcher {

    int[] groups;

    int groupCount;

    int first;

    int last;

    Map<String, Integer> namedGroups;

    Pattern pattern;

    InputAdapter input;

    VirtualMachine machine;

    public Matcher(Pattern pattern, CharSequence input) {
        this.pattern = pattern;
        this.input = new UTF16Input(input);
        this.machine = new VirtualMachine();
        this.groups = new int[(pattern.getCaptureCount()+1)*2];
        this.namedGroups = new HashMap<String, Integer>();
        reset();
    }

    public boolean matches() {
        machine.execute(this);
        return first == input.start() && last == input.end();
    }



    public String group() {
        return group(0);
    }

    public String group(int group) {
        return input.getSubSequence(groups[group*2], groups[group*2+1]);
    }

    public String group(String name) {
        int group = namedGroups.get(name);
        return group(group);
    }



    public void reset() {
        Arrays.fill(groups, -1);
        this.first = -1;
        this.last = -1;
        this.groupCount = 0;
        this.namedGroups.clear();
    }

    public int start() {
        if (first < 0)
            throw new IllegalStateException("No match available");
        return first;
    }

    public int start(int group) {
        if (first < 0)
            throw new IllegalStateException("No match available");
        if (group < 0 || group > groupCount)
            throw new IndexOutOfBoundsException("No group " + group);
        return groups[group * 2];
    }

    public int start(String name) {
        return groups[namedGroups.get(name) * 2];
    }

    public int end() {
        if (first < 0)
            throw new IllegalStateException("No match available");
        return last;
    }

    public int end(int group) {
        if (first < 0)
            throw new IllegalStateException("No match available");
        if (group < 0 || group > groupCount)
            throw new IndexOutOfBoundsException("No group " + group);
        return groups[group * 2 + 1];
    }

    public int end(String name) {
        return groups[namedGroups.get(name) * 2 + 1];
    }
}

package org.xiaoheshan.simplere2j.parser.handler;

import org.xiaoheshan.simplere2j.ast.AstNode;
import org.xiaoheshan.simplere2j.ast.leaf.CharClassNode;
import org.xiaoheshan.simplere2j.scanner.ScanSource;
import org.xiaoheshan.simplere2j.utils.ArrayUtil;

import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * @author _Chf
 * @date 2017-09-23
 */
public class DotCaseHandler extends AbstractCaseHandler {
    @Override
    public char[] matchedCase() {
        return new char[]{'.'};
    }

    @Override
    public void handle(ScanSource source, Deque<AstNode> stack, int mode) {
        source.skip();
        Set<Integer> characters = new HashSet<Integer>();
        Collections.addAll(characters, ArrayUtil.supportChars());
        stack.push(new CharClassNode(characters));
    }
}

package org.xiaoheshan.simplere2j.parser.handler;

import org.xiaoheshan.simplere2j.ast.AstNode;
import org.xiaoheshan.simplere2j.ast.NoneNode;
import org.xiaoheshan.simplere2j.scanner.ScanSource;

import java.util.Deque;

/**
 * @author _Chf
 * @date 2017-09-20
 */
public class BarCaseHandler extends AbstractCaseHandler {

    @Override
    public char[] matchedCase() {
        return new char[] {'|'};
    }

    @Override
    public void handle(ScanSource source, Deque<AstNode> stack, int mode) {
        BarMarkNode markNode = new BarMarkNode();
        stack.push(markNode);
        source.skip();
    }

    public final static class BarMarkNode extends NoneNode {
    }

}

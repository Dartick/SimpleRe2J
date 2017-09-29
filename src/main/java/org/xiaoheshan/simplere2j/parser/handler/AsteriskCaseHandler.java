package org.xiaoheshan.simplere2j.parser.handler;

import org.xiaoheshan.simplere2j.ast.AstNode;
import org.xiaoheshan.simplere2j.ast.composite.ClosureNode;
import org.xiaoheshan.simplere2j.ast.composite.StarNode;
import org.xiaoheshan.simplere2j.scanner.ScanSource;

import java.util.Deque;

/**
 * @author _Chf
 * @date 2017-09-20
 */
public class AsteriskCaseHandler extends AbstractCaseHandler {

    @Override
    public char[] matchedCase() {
        return new char[]{'*'};
    }

    @Override
    public void handle(ScanSource source, Deque<AstNode> stack, int mode) {

        source.skip();

        AstNode top = stack.pop();

        ClosureNode.Type type = ClosureNode.Type.getType(source.peek());

        stack.push(new StarNode(type, new AstNode[]{top}));

    }
}

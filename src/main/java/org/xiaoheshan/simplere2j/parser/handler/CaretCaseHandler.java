package org.xiaoheshan.simplere2j.parser.handler;

import org.xiaoheshan.simplere2j.ast.AstNode;
import org.xiaoheshan.simplere2j.ast.leaf.BoundaryNode;
import org.xiaoheshan.simplere2j.scanner.ScanSource;

import java.util.Deque;

/**
 * @author _Chf
 * @date 2017-09-20
 */
public class CaretCaseHandler extends AbstractCaseHandler {

    @Override
    public char[] matchedCase() {
        return new char[]{'^'};
    }

    @Override
    public void handle(ScanSource source, Deque<AstNode> stack, int mode) {
        source.skip();
        stack.push(new BoundaryNode(BoundaryNode.Type.BEGIN_LINE));
    }
}

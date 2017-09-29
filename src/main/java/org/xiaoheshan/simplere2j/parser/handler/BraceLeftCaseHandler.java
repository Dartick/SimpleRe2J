package org.xiaoheshan.simplere2j.parser.handler;

import org.xiaoheshan.simplere2j.ast.AstNode;
import org.xiaoheshan.simplere2j.ast.composite.ClosureNode;
import org.xiaoheshan.simplere2j.scanner.ScanSource;

import java.util.Deque;

/**
 * @author _Chf
 * @date 2017-09-20
 */
public class BraceLeftCaseHandler extends AbstractCaseHandler {

    @Override
    public char[] matchedCase() {
        return new char[]{'{'};
    }

    @Override
    public void handle(ScanSource source, Deque<AstNode> stack, int mode) {
        source.skip();
        StringBuilder minBuilder = new StringBuilder();
        StringBuilder maxBuilder  = new StringBuilder();
        int min = -1;
        int max = -1;
        while (!source.isEnd()) {
            if (source.peek() == ',') {
                source.skip();
                break;
            }
            if (source.peek() == '}') {
                max = 0;
                break;
            }
            minBuilder.append(source.poll());
        }
        while (!source.isEnd()) {
            if (source.peek() == '}') {
                source.skip();
                break;
            }
            maxBuilder.append(source.poll());
        }

        if (minBuilder.length() == 0) {
            // throw error
        }
        else {
            min = Integer.valueOf(minBuilder.toString());
        }
        if (maxBuilder.length() != 0) {
            max = Integer.valueOf(maxBuilder.toString());
        }

        AstNode top = stack.pop();
        ClosureNode.Type type = ClosureNode.Type.getType(source.peek());
        stack.push(new ClosureNode(min, max, type, new AstNode[]{top}));
    }
}

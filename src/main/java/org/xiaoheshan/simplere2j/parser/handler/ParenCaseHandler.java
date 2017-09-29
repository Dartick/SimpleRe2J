package org.xiaoheshan.simplere2j.parser.handler;

import org.xiaoheshan.simplere2j.ast.*;
import org.xiaoheshan.simplere2j.ast.composite.AssertNode;
import org.xiaoheshan.simplere2j.ast.composite.CaptureNode;
import org.xiaoheshan.simplere2j.scanner.ScanSource;
import org.xiaoheshan.simplere2j.utils.ParseUtil;

import java.util.*;

/**
 * @author _Chf
 * @date 2017-09-20
 */
public class ParenCaseHandler extends AbstractCaseHandler {

    private static final Character PAREN_LEFT = '(';
    private static final Character PAREN_RIGHT = ')';
    private static final List<CaptureType> CAPTURE_TYPES = new ArrayList<>();

    static {
        Collections.addAll(CAPTURE_TYPES, AssertNode.Type.values());
        Collections.addAll(CAPTURE_TYPES, CaptureNode.Type.values());
    }

    @Override
    public char[] matchedCase() {
        return new char[]{PAREN_LEFT, PAREN_RIGHT};
    }

    @Override
    public void handle(ScanSource source, Deque<AstNode> stack, int mode) {

        if (PAREN_LEFT.equals(source.peek())) {
            handleLeft(source, stack, mode);
        }
        else {
            handleRight(source, stack, mode);
        }

    }

    private void handleLeft(ScanSource source, Deque<AstNode> stack, int mode) {
        for (CaptureType type : CAPTURE_TYPES) {
            int length = type.getPrefixLength();
            String sub = source.sub(length);
            String name = "";
            if (type.is(sub)) {
                source.skip(length);
                if (type.isNameType()) {
                    name = subName(source);
                }
                ParenMarkNode markNode = new ParenMarkNode(name , type);
                stack.push(markNode);
                break;
            }
        }
    }

    private void handleRight(ScanSource source, Deque<AstNode> stack, int mode) {
        source.skip();
        Deque<AstNode> grandson = new LinkedList<AstNode>();
        while (!stack.isEmpty()) {
            AstNode node = stack.pop();
            if (node instanceof ParenMarkNode) {
                ParenMarkNode markNode = (ParenMarkNode) node;
                AstNode son = ParseUtil.compact(grandson);
                if (markNode.type instanceof CaptureNode.Type) {
                    CaptureNode.Type type = (CaptureNode.Type) markNode.type;
                    stack.push(new CaptureNode(markNode.name, type, new AstNode[] {son}));
                    break;
                }
                else if (markNode.type instanceof AssertNode.Type){
                    AssertNode.Type type = (AssertNode.Type) markNode.type;
                    stack.push(new AssertNode(type, new AstNode[] {son}));
                    break;
                }
            }
            grandson.push(node);
        }
    }

    private String subName(ScanSource source) {
        if (source.next() == '>') {
            //throw error
        }
        StringBuilder builder = new StringBuilder();
        while (source.hasNext()) {
            if (source.peek() == '>') {
                source.skip();
                break;
            }
            builder.append(source.poll());
        }
        return builder.toString();
    }

    final static class ParenMarkNode extends NoneNode {
        String name;
        CaptureType type;
        ParenMarkNode(String name, CaptureType type) {
            this.name = name;
            this.type = type;
        }
    }

}

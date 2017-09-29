package org.xiaoheshan.simplere2j.utils;

import org.xiaoheshan.simplere2j.ast.AstNode;
import org.xiaoheshan.simplere2j.ast.composite.AndNode;
import org.xiaoheshan.simplere2j.ast.composite.OrNode;
import org.xiaoheshan.simplere2j.parser.handler.BarCaseHandler;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author _Chf
 * @date 2017-09-24
 */
public final class ParseUtil {

    private ParseUtil() {
    }

    public static AstNode compact(Deque<AstNode> stack) {

        if (stack.isEmpty()) {
            return null;
        }

        if (stack.size() == 1) {
            return stack.peek();
        }

        List<AstNode> xx = new ArrayList<AstNode>();
        List<AstNode> yy = new ArrayList<AstNode>();
        while(!stack.isEmpty()) {
            AstNode node = stack.pollLast();
            if (node instanceof BarCaseHandler.BarMarkNode) {
                transfer(xx, yy);
                xx.clear();
                yy.add(node);
                continue;
            }
            xx.add(node);
        }
        transfer(xx, yy);
        xx.clear();

        boolean isOr = false;

        for (AstNode node : yy) {
            if (node instanceof BarCaseHandler.BarMarkNode) {
                isOr = true;
                continue;
            }
            xx.add(node);
        }

        if (xx.size() == 1) {
            return xx.get(0);
        }
        AstNode[] children = xx.toArray(new AstNode[xx.size()]);
        return isOr? new OrNode(children) : new AndNode(children);

    }

    private static void transfer(List<AstNode> from, List<AstNode> to) {
        if (from.size() == 1) {
            to.add(from.get(0));
        }
        else {
            to.add(new AndNode(from.toArray(new AstNode[from.size()])));
        }
    }

}
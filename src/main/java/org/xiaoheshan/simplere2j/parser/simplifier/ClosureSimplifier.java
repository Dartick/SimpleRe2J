package org.xiaoheshan.simplere2j.parser.simplifier;

import org.xiaoheshan.simplere2j.ast.AstNode;
import org.xiaoheshan.simplere2j.ast.composite.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author _Chf
 * @date 2017-09-24
 */
public class ClosureSimplifier extends Simplifier {

    @Override
    AstNode simplify(AstNode root) {
        return cloneInternal(root);
    }

    private AstNode cloneInternal(AstNode node) {
        if (!node.isLeafNode()) {
            AstNode[] children = node.getChildren();
            for (int i = 0; i < children.length; i++) {
                children[i] = cloneInternal(children[i]);
            }
        }
        if (node instanceof PlusNode) {
            return plusSimplify((PlusNode) node);
        }
        else if (node instanceof ClosureNode) {
            return closureSimplify((ClosureNode) node);
        }
        return node;
    }

    private AstNode closureSimplify(ClosureNode node) {

        int min = node.getMin();
        int max = node.getMax();
        ClosureNode.Type type = node.getType();
        AstNode[] children = node.getChildren();

        if (min == -1 && max == -1) {
            return new StarNode(type, children);
        }

        AstNode child = children[0];
        List<AstNode> childrenList = new ArrayList<AstNode>();
        for (int i = 0; i < min; i++) {
            childrenList.add(child);
        }
        if (max == -1) {
            childrenList.add(new StarNode(type, new AstNode[]{child}));
        }
        for (int i = 0; i < max - min; i++) {
            childrenList.add(new QuestionNode(type, new AstNode[]{child}));
        }

        return new AndNode( childrenList.toArray(new AstNode[childrenList.size()]));
    }

    private AstNode plusSimplify(PlusNode node) {
        return new AndNode(
                new AstNode[]{
                        node.getChildren()[0] ,
                        new StarNode(
                                node.getType(),
                                new AstNode[]{node.getChildren()[0]}
                        )
                }
        );
    }

}

package org.xiaoheshan.simplere2j.parser.simplifier;

import org.xiaoheshan.simplere2j.ast.AstNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author _Chf
 * @date 2017-09-25
 */
public final class Simplifiers {

    private List<Simplifier> supports = new ArrayList<Simplifier>();

    private Simplifiers() {
        ClosureSimplifier closureSimplifier = new ClosureSimplifier();
        supports.add(closureSimplifier);
    }

    public static AstNode simplify(AstNode root) {
        Simplifiers simplifiers = new Simplifiers();
        for (Simplifier support : simplifiers.supports) {
            root = support.simplify(root);
        }
        return root;
    }

}

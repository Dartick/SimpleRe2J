package org.xiaoheshan.simplere2j.ast.leaf;

import org.xiaoheshan.simplere2j.ast.AbstractLeafAstNode;
import org.xiaoheshan.simplere2j.ast.AstVisitor;

import java.util.Set;

/**
 * @author _Chf
 * @date 2017-09-22
 */
public class CharClassNode extends AbstractLeafAstNode<Set<Integer>> {

    private Set<Integer> contains;

    public CharClassNode(Set<Integer> contains) {
        this.contains = contains;
    }

    @Override
    public Set<Integer> getValue() {
        return contains;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "char-class";
    }
}

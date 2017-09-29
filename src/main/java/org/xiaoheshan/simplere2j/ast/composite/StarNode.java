package org.xiaoheshan.simplere2j.ast.composite;

import org.xiaoheshan.simplere2j.ast.AstNode;
import org.xiaoheshan.simplere2j.ast.AstVisitor;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public class StarNode extends ClosureNode {

    public StarNode(Type type, AstNode[] astNodes) {
        super(0, -1, type, astNodes);
    }

    @Override
    protected String prefixString() {
        return "";
    }

    @Override
    protected String middleString() {
        return "";
    }

    @Override
    protected String suffixString() {
        return "*" + super.getType().getName();
    }

    @Override
    protected void acceptBefore(AstVisitor visitor) {
        visitor.visit(this).visitBefore(this);
    }

    @Override
    protected void acceptAfter(AstVisitor visitor) {
        visitor.visit(this).visitAfter(this);
    }
}

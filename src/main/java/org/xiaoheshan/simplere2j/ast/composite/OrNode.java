package org.xiaoheshan.simplere2j.ast.composite;

import org.xiaoheshan.simplere2j.ast.AbstractCompositeNode;
import org.xiaoheshan.simplere2j.ast.AstNode;
import org.xiaoheshan.simplere2j.ast.AstVisitor;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public class OrNode extends AbstractCompositeNode {

    public OrNode(AstNode[] astNodes) {
        super(astNodes);
    }

    @Override
    protected String prefixString() {
        return "";
    }

    @Override
    protected String middleString() {
        return "|";
    }

    @Override
    protected String suffixString() {
        return "";
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

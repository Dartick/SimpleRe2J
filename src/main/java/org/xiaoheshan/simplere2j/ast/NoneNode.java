package org.xiaoheshan.simplere2j.ast;

/**
 * @author _Chf
 * @date 2017-09-21
 */
public abstract class NoneNode implements AstNode {
    @Override
    public Object getValue() {
        throw new UnsupportedOperationException("this is a none node");
    }

    @Override
    public AstNode[] getChildren() {
        throw new UnsupportedOperationException("this is a none node");
    }

    @Override
    public boolean isLeafNode() {
        throw new UnsupportedOperationException("this is a none node");
    }

    @Override
    public void accept(AstVisitor visitor) {
        throw new UnsupportedOperationException("this is a none node");
    }

}

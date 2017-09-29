package org.xiaoheshan.simplere2j.ast;

/**
 * @author _Chf
 * @date 2017-09-19
 */
public abstract class AbstractLeafAstNode<V> implements AstNode<V> {

    @Override
    public final AstNode[] getChildren() {
        throw new UnsupportedOperationException("this is a leaf node");
    }

    @Override
    public final boolean isLeafNode() {
        return true;
    }

}

package org.xiaoheshan.simplere2j.ast;

/**
 * @author _Chf
 * @date 2017-09-19
 */
public interface AstNode<V> {

    V getValue();

    AstNode[] getChildren();

    boolean isLeafNode();

    void accept(AstVisitor visitor);

}

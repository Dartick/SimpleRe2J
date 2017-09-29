package org.xiaoheshan.simplere2j.ast;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public interface VisitorHandler {

    void visitBefore(AstNode node);

    void visitAfter(AstNode node);
}

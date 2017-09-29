package org.xiaoheshan.simplere2j.ast;

import org.xiaoheshan.simplere2j.ast.composite.*;
import org.xiaoheshan.simplere2j.ast.leaf.BoundaryNode;
import org.xiaoheshan.simplere2j.ast.leaf.CharClassNode;
import org.xiaoheshan.simplere2j.ast.leaf.LiteralNode;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public interface AstVisitor {

    void visit(BoundaryNode node);

    void visit(CharClassNode node);

    void visit(LiteralNode node);

    VisitorHandler visit(OrNode node);

    VisitorHandler visit(AndNode node);

    VisitorHandler visit(CaptureNode node);

    VisitorHandler visit(AssertNode node);

    VisitorHandler visit(ClosureNode node);

    VisitorHandler visit(PlusNode node);

    VisitorHandler visit(StarNode node);

    VisitorHandler visit(QuestionNode node);
}

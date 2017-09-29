package org.xiaoheshan.simplere2j.parser.simplifier;

import org.xiaoheshan.simplere2j.ast.AstNode;

/**
 * @author _Chf
 * @date 2017-09-24
 */
public abstract class Simplifier {

    abstract AstNode simplify(AstNode root);
}

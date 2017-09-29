package org.xiaoheshan.simplere2j.parser;

import org.xiaoheshan.simplere2j.ast.AstNode;

/**
 * @author _Chf
 * @date 2017-09-20
 */
public interface Parser {

    AstNode parse(String expression);

}

package org.xiaoheshan.simplere2j.parser.handler;

import org.xiaoheshan.simplere2j.ast.AstNode;
import org.xiaoheshan.simplere2j.scanner.ScanSource;

import java.util.Deque;

/**
 * @author _Chf
 * @date 2017-09-20
 */
public interface CaseHandler {

    boolean matched(char ch);

    char[] matchedCase();

    void handle(ScanSource source, Deque<AstNode> stack, int mode);
}

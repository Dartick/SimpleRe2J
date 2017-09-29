package org.xiaoheshan.simplere2j.parser;

import org.xiaoheshan.simplere2j.ast.AstNode;
import org.xiaoheshan.simplere2j.parser.handler.CaseHandler;
import org.xiaoheshan.simplere2j.parser.handler.CaseHandlers;
import org.xiaoheshan.simplere2j.parser.simplifier.Simplifiers;
import org.xiaoheshan.simplere2j.scanner.ScanSource;
import org.xiaoheshan.simplere2j.utils.ParseUtil;

import java.util.*;

/**
 * @author _Chf
 * @date 2017-09-20
 */
public class GenericParser implements Parser {

    @Override
    public AstNode parse(String expression) {
        ScanSource source = new ScanSource(expression);
        Deque<AstNode> stack = new LinkedList<AstNode>();
        Set<CaseHandler> supportParseCase = CaseHandlers.getSupportParseCases();

        while (!source.isEnd()) {
            for (CaseHandler caseHandler : supportParseCase) {
                if (caseHandler.matched(source.peek())) {
                    caseHandler.handle(source, stack, 0);
                    break;
                }
            }
        }

        AstNode root = ParseUtil.compact(stack);

        root = Simplifiers.simplify(root);

        return root;
    }

}


package org.xiaoheshan.simplere2j.parser.handler;

import org.xiaoheshan.simplere2j.utils.ArrayUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author _Chf
 * @date 2017-09-20
 */
public final class CaseHandlers {

    private static final AsteriskCaseHandler ASTERISK = new AsteriskCaseHandler();
    private static final BackslashCaseHandler BACKSLASH = new BackslashCaseHandler();
    private static final BarCaseHandler BAR = new BarCaseHandler();
    private static final BraceLeftCaseHandler BRACE_LEFT = new BraceLeftCaseHandler();
    private static final BracketLeftCaseHandler BRACKET_LEFT = new BracketLeftCaseHandler();
    private static final CaretCaseHandler CARET = new CaretCaseHandler();
    private static final DollarCaseHandler DOLLAR = new DollarCaseHandler();
    private static final LiteralCaseHandler LITERAL = new LiteralCaseHandler();
    private static final ParenCaseHandler PAREN = new ParenCaseHandler();
    private static final PlusCaseHandler PLUS = new PlusCaseHandler();
    private static final QuestionCaseHandler QUESTION = new QuestionCaseHandler();
    private static final DotCaseHandler DOT = new DotCaseHandler();

    private static final Set<CaseHandler> SUPPORT_PARSE_CASES = new HashSet<CaseHandler>();
    private static final Set<Character> SUPPORT_OPERATION_CHARS = new HashSet<Character>();

    static {
        initSupportParseCases();
        initSupportOperationChars();
    }

    private CaseHandlers() {
    }

    public static Set<CaseHandler> getSupportParseCases() {
        return SUPPORT_PARSE_CASES;
    }

    public static Set<Character> getSupportOperationChars() {
        return SUPPORT_OPERATION_CHARS;
    }

    private static void initSupportParseCases() {
        SUPPORT_PARSE_CASES.add(ASTERISK);
        SUPPORT_PARSE_CASES.add(BACKSLASH);
        SUPPORT_PARSE_CASES.add(BAR);
        SUPPORT_PARSE_CASES.add(BRACE_LEFT);
        SUPPORT_PARSE_CASES.add(BRACKET_LEFT);
        SUPPORT_PARSE_CASES.add(CARET);
        SUPPORT_PARSE_CASES.add(DOLLAR);
        SUPPORT_PARSE_CASES.add(LITERAL);
        SUPPORT_PARSE_CASES.add(PAREN);
        SUPPORT_PARSE_CASES.add(PLUS);
        SUPPORT_PARSE_CASES.add(QUESTION);
        SUPPORT_PARSE_CASES.add(DOT);
    }

    private static void initSupportOperationChars() {
        for (CaseHandler supportParseCase : SUPPORT_PARSE_CASES) {
            Collections.addAll(SUPPORT_OPERATION_CHARS, ArrayUtil.makeCharArray(supportParseCase.matchedCase()));
        }
    }

}

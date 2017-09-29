package org.xiaoheshan.simplere2j.pattern;

import org.xiaoheshan.simplere2j.ast.AstNode;
import org.xiaoheshan.simplere2j.compiler.Compiler;
import org.xiaoheshan.simplere2j.matcher.Matcher;
import org.xiaoheshan.simplere2j.parser.GenericParser;
import org.xiaoheshan.simplere2j.parser.Parser;
import org.xiaoheshan.simplere2j.program.Program;

/**
 * @author _Chf
 * @date 2017-09-29
 */
public class Pattern {

    private Parser parser;

    private Compiler compiler;

    private Program program;

    private int captureCount;

    private Pattern() {
        parser = new GenericParser();
        compiler = new Compiler();
    }

    public static Pattern compile(String regex) {
        Pattern pattern = new Pattern();
        AstNode root = pattern.parser.parse(regex);
        pattern.program = pattern.compiler.compile(root, 0);
        pattern.captureCount = pattern.program.getCaptureCount();
        return pattern;
    }

    public Matcher matcher(CharSequence input) {
        return new Matcher(this, input);
    }

    public Program getProgram() {
        return program;
    }

    public int getCaptureCount() {
        return captureCount;
    }
}

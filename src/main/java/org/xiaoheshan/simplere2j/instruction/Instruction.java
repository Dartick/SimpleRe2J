package org.xiaoheshan.simplere2j.instruction;

import java.util.Arrays;

/**
 * @author _Chf
 * @date 2017-09-19
 */
public abstract class Instruction {

    private int[] next;
    private int[] operands;
    private Object attachment;

    public Instruction() {
    }

    public Instruction(int[] operands) {
        this.operands = operands;
    }

    public int[] getOperands() {
        return operands;
    }

    public void setNext(int[] next) {
        this.next = next;
    }

    public int[] getNext() {
        return next;
    }

    public void attach(Object attachment) {
        this.attachment = attachment;
    }

    public Object getAttachment() {
        return attachment;
    }

    @Override
    public String toString() {
        return opcodeString() + " " +
                operandString() +
                " -> " +
                Arrays.toString(next);
    }

    protected abstract String opcodeString();
    protected abstract String operandString();
    public abstract void accept(int sp, IExecutor executor);

    public enum Opcode {
        START,
        EPSILON,
        BRANCH,
        CLOSURE,
        RUNE,
        CHAR_CLASS,
        STORAGE_CAPTURE,
        NORMAL_CAPTURE,
        NONE_CAPTURE,
        POSITIVE_LOOKAHEAD_ASSERTION,
        POSITIVE_LOOKBEHIND_ASSERTION,
        NEGATIVE_LOOKAHEAD_ASSERTION,
        NEGATIVE_LOOKBEHIND_ASSERTION,
        BEGIN_LINE_BOUNDARY,
        END_LINE_BOUNDARY,
        END,
        MATCH
    }

}

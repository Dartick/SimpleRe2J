package org.xiaoheshan.simplere2j.instruction;

/**
 * @author _Chf
 * @date 2017-09-28
 */
public class AssertionInstruction extends Instruction {

    private Type type;

    public AssertionInstruction(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    protected String opcodeString() {
        return type.name;
    }

    @Override
    protected String operandString() {
        return "";
    }

    @Override
    public void accept(IExecutor executor) {
        executor.execute(this);
    }

    public enum Type {
        POSITIVE_LOOKAHEAD("positive-lookahead"),
        POSITIVE_LOOKBEHIND("positive-lookbehind"),
        NEGATIVE_LOOKAHEAD("negative-lookahead"),
        NEGATIVE_LOOKBEHIND("negative-lookbehind");

        private String name;

        Type(String name) {
            this.name = name;
        }
    }

}

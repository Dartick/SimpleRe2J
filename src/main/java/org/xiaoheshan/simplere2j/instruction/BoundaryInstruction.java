package org.xiaoheshan.simplere2j.instruction;

/**
 * @author _Chf
 * @date 2017-09-28
 */
public class BoundaryInstruction extends Instruction {

    private Type type;

    public BoundaryInstruction(Type type) {
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
    public void accept(int sp, IExecutor executor) {
        executor.execute(sp, this);
    }

    public enum Type {
        BEGIN_LINE("begin-line"),
        END_LINE_BOUNDARY("end-line");

        private String name;

        Type(String name) {
            this.name = name;
        }
    }

}

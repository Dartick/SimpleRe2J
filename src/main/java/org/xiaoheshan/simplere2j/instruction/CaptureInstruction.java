package org.xiaoheshan.simplere2j.instruction;

/**
 * @author _Chf
 * @date 2017-09-28
 */
public class CaptureInstruction extends Instruction {

    private Type type;

    public CaptureInstruction(String name, Type type) {
        this.type = type;
        super.attach(name);
    }

    public String getName() {
        return (String) super.getAttachment();
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
        return getName();
    }

    @Override
    public void accept(IExecutor executor) {
        executor.execute(this);
    }

    public enum Type {
        NAME("storage-capture"),
        NONE("none-capture"),
        NORMAL("normal-capture");

        private String name;

        Type(String name) {
            this.name = name;
        }
    }

}

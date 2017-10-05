package org.xiaoheshan.simplere2j.instruction;

/**
 * @author _Chf
 * @date 2017-10-05
 */
abstract class CaptureInstruction extends Instruction {

    private Type type;

    public CaptureInstruction(String name, int[] operands, Type type) {
        super(operands);
        this.type = type;
        attach(name);
    }

    public String getName() {
        return (String) getAttachment();
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        NAME("storage-capture"),
        NONE("none-capture"),
        NORMAL("normal-capture");

        protected String name;

        Type(String name) {
            this.name = name;
        }
    }

}

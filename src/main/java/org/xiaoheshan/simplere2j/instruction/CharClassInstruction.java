package org.xiaoheshan.simplere2j.instruction;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public class CharClassInstruction extends Instruction {

    @Override
    public void accept(IExecutor executor) {
        executor.execute(this);
    }

    @Override
    protected String opcodeString() {
        return "char-class";
    }

    @Override
    protected String operandString() {
        return "";
    }
}

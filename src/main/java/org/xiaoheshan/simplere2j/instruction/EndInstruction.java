package org.xiaoheshan.simplere2j.instruction;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public class EndInstruction extends Instruction {
    @Override
    protected String opcodeString() {
        return "end";
    }

    @Override
    protected String operandString() {
        return "";
    }

    @Override
    public void accept(int sp, IExecutor executor) {
        executor.execute(sp, this);
    }
}

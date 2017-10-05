package org.xiaoheshan.simplere2j.instruction;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public class ClosureInstruction extends Instruction {
    @Override
    public void accept(int sp, IExecutor executor) {
        executor.execute(sp, this);
    }

    @Override
    protected String opcodeString() {
        return "closure";
    }

    @Override
    protected String operandString() {
        return "";
    }
}

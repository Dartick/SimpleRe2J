package org.xiaoheshan.simplere2j.instruction;

import java.util.Arrays;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public class ClosureInstruction extends Instruction {
    @Override
    public void accept(IExecutor executor) {
        executor.execute(this);
    }

    @Override
    protected String opcodeString() {
        return "closure";
    }

    @Override
    protected String operandString() {
        return Arrays.toString(getOperands());
    }
}

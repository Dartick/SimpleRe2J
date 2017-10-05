package org.xiaoheshan.simplere2j.instruction;

import java.util.Arrays;

/**
 * @author _Chf
 * @date 2017-10-05
 */
public class CaptureEndInstruction extends CaptureInstruction {

    public CaptureEndInstruction(String name, int[] operands, Type type) {
        super(name, operands, type);
    }

    @Override
    protected String opcodeString() {
        return "end " + getType().name;
    }

    @Override
    protected String operandString() {
        return Arrays.toString(getOperands());
    }

    @Override
    public void accept(int sp, IExecutor executor) {
        executor.execute(sp, this);
    }

}

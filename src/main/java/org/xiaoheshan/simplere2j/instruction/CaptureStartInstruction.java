package org.xiaoheshan.simplere2j.instruction;

import java.util.Arrays;

/**
 * @author _Chf
 * @date 2017-09-28
 */
public class CaptureStartInstruction extends CaptureInstruction {

    public CaptureStartInstruction(String name, int[] operands, Type type) {
        super(name, operands, type);
    }

    @Override
    protected String opcodeString() {
        return "start " + getType().name;
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

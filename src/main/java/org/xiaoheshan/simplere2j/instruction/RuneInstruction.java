package org.xiaoheshan.simplere2j.instruction;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public class RuneInstruction extends Instruction {

    public RuneInstruction(int[] operands) {
        super(operands);
    }

    @Override
    public void accept(IExecutor executor) {
        executor.execute(this);
    }

    @Override
    protected String opcodeString() {
        return "";
    }

    @Override
    protected String operandString() {
        StringBuilder builder = new StringBuilder();
        for (int operand : getOperands()) {
            builder.append((char) operand);
        }
        return builder.toString();
    }
}

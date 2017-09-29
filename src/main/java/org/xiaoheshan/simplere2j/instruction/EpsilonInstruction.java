package org.xiaoheshan.simplere2j.instruction;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public class EpsilonInstruction extends Instruction {

    @Override
    public void accept(IExecutor executor) {
        executor.execute(this);
    }

    @Override
    protected String opcodeString() {
        return "epsilon";
    }

    @Override
    protected String operandString() {
        return "";
    }

}

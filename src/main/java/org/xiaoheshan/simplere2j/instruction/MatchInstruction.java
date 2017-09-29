package org.xiaoheshan.simplere2j.instruction;

/**
 * @author _Chf
 * @date 2017-09-28
 */
public class MatchInstruction extends Instruction {

    @Override
    protected String opcodeString() {
        return "match";
    }

    @Override
    protected String operandString() {
        return "";
    }

    @Override
    public void accept(IExecutor executor) {
        executor.execute(this);
    }
}

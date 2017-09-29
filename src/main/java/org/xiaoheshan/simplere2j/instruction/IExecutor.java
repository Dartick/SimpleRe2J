package org.xiaoheshan.simplere2j.instruction;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public interface IExecutor {

    void execute(EpsilonInstruction instruction);

    void execute(BranchInstruction instruction);

    void execute(ClosureInstruction instruction);

    void execute(RuneInstruction instruction);

    void execute(CharClassInstruction instruction);

    void execute(CaptureInstruction instruction);

    void execute(AssertionInstruction instruction);

    void execute(BoundaryInstruction instruction);

    void execute(EndInstruction instruction);

    void execute(MatchInstruction instruction);
}

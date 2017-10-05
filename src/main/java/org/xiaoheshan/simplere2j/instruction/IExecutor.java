package org.xiaoheshan.simplere2j.instruction;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public interface IExecutor {

    void execute(int sp, EpsilonInstruction instruction);

    void execute(int sp, BranchInstruction instruction);

    void execute(int sp, ClosureInstruction instruction);

    void execute(int sp, RuneInstruction instruction);

    void execute(int sp, CharClassInstruction instruction);

    void execute(int sp, CaptureStartInstruction instruction);

    void execute(int sp, CaptureEndInstruction instruction);

    void execute(int sp, AssertionInstruction instruction);

    void execute(int sp, BoundaryInstruction instruction);

    void execute(int sp, EndInstruction instruction);

    void execute(int sp, MatchInstruction instruction);
}

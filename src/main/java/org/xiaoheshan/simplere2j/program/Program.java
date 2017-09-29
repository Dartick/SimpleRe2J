package org.xiaoheshan.simplere2j.program;

import org.xiaoheshan.simplere2j.instruction.Instruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author _Chf
 * @date 2017-09-23
 */
public class Program {

    private int start;
    private int end;
    private int captureCount;
    private Instruction[] instructions;

    public Program(int start, int end, int captureCount, Instruction[] instructions) {
        this.start = start;
        this.end = end;
        this.captureCount = captureCount;
        this.instructions = instructions;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getCaptureCount() {
        return captureCount;
    }

    public Instruction[] getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        return Arrays.toString(instructions);
    }
}

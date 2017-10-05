package org.xiaoheshan.simplere2j.instruction;

import org.xiaoheshan.simplere2j.ast.composite.AssertNode;
import org.xiaoheshan.simplere2j.ast.composite.CaptureNode;
import org.xiaoheshan.simplere2j.ast.leaf.BoundaryNode;

import java.util.Set;

/**
 * @author _Chf
 * @date 2017-09-25
 */
public final class Instructions {

    private Instructions() {
    }

    public static Instruction newEpsilon() {
        return new EpsilonInstruction();
    }

    public static Instruction newRune(int... rune) {
        return new RuneInstruction(rune);
    }

    public static Instruction newCharClass(Set<Integer> contains) {
        Instruction instruction = new CharClassInstruction();
        instruction.attach(contains);
        return instruction;
    }

    public static Instruction newBranch() {
        return new BranchInstruction();
    }

    public static Instruction newClosure() {
        return new ClosureInstruction();
    }

    public static Instruction newCaptureStart(String name, int captureIndex,CaptureNode.Type type) {
        switch (type) {
            case NAME:
                return new CaptureStartInstruction(name, new int[]{captureIndex}, CaptureInstruction.Type.NAME);
            case DEFAULT:
                return new CaptureStartInstruction(name, new int[]{captureIndex}, CaptureInstruction.Type.NORMAL);
            case NONE:
                return new CaptureStartInstruction(name, new int[]{captureIndex}, CaptureInstruction.Type.NONE);
        }

        throw new IllegalArgumentException("this is not a capture type");
    }

    public static Instruction newCaptureEnd(String name, int captureIndex,CaptureNode.Type type) {
        switch (type) {
            case NAME:
                return new CaptureEndInstruction(name, new int[]{captureIndex}, CaptureInstruction.Type.NAME);
            case DEFAULT:
                return new CaptureEndInstruction(name, new int[]{captureIndex}, CaptureInstruction.Type.NORMAL);
            case NONE:
                return new CaptureEndInstruction(name, new int[]{captureIndex}, CaptureInstruction.Type.NONE);
        }

        throw new IllegalArgumentException("this is not a capture type");
    }

    public static Instruction newAssertion(AssertNode.Type type) {
        switch (type) {
            case POSITIVE_LOOKAHEAD:
                return new AssertionInstruction(AssertionInstruction.Type.POSITIVE_LOOKAHEAD);
            case NEGATIVE_LOOKAHEAD:
                return new AssertionInstruction(AssertionInstruction.Type.NEGATIVE_LOOKAHEAD);
            case POSITIVE_LOOKBEHIND:
                return new AssertionInstruction(AssertionInstruction.Type.POSITIVE_LOOKBEHIND);
            case NEGATIVE_LOOKBEHIND:
                return new AssertionInstruction(AssertionInstruction.Type.NEGATIVE_LOOKBEHIND);
        }

        throw new IllegalArgumentException("this is not a assert type");
    }

    public static Instruction newBoundary(BoundaryNode.Type type) {
        switch (type) {
            case BEGIN_LINE:
                return new BoundaryInstruction(BoundaryInstruction.Type.BEGIN_LINE);
            case END_LINE:
                return new BoundaryInstruction(BoundaryInstruction.Type.END_LINE_BOUNDARY);
        }

        throw new IllegalArgumentException("this is not a boundary type");
    }

    public static Instruction newEnd() {
        return new EndInstruction();
    }

    public static Instruction newMatch() {
        return new MatchInstruction();
    }

}


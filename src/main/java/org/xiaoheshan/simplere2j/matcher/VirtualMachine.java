package org.xiaoheshan.simplere2j.matcher;

import org.xiaoheshan.simplere2j.instruction.*;
import org.xiaoheshan.simplere2j.program.Program;

import java.util.*;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public class VirtualMachine {

    private ExecuteContext context = new ExecuteContext();
    private InstExecutor instExecutor = new InstExecutor();

    static final int NOANCHOR = 0;
    static final int ENDANCHOR = 1;

    VirtualMachine() {
    }

    public boolean execute(Matcher matcher, int anchor) {

        initContext(matcher, anchor);

        switch (context.acceptMode) {
            case NOANCHOR:
                executeNoAnchor();
                break;
            case ENDANCHOR:
                executeEndAnchor();
                break;
        }

        if (!context.matched) {
            context.matcher.last = context.matcher.first;
        }

        boolean result = context.matched;

        clearContext();

        return result;
    }

    private void executeEndAnchor() {
        context.matcher.groups[0] = context.matcher.first;
        context.matcher.groupCount++;
        ExecuteThread thread = new ExecuteThread(context.matcher.first,
                context.program.getInstructions()[context.program.getStart()]);

        context.threadStack.push(thread);

        while (!context.matched && !context.threadStack.isEmpty()) {
            ExecuteThread currentThread = context.threadStack.peek();
            currentThread.inst.accept(currentThread.sp, instExecutor);
        }
    }

    private void executeNoAnchor() {
        context.matcher.groups[0] = context.matcher.first;
        context.matcher.groupCount++;
        ExecuteThread thread = new ExecuteThread(context.matcher.first,
                context.program.getInstructions()[context.program.getStart()]);

        context.threadStack.push(thread);

        while (!context.matched && !context.input.isEnd(context.matcher.first)) {
            if (context.threadStack.isEmpty()) {
                context.matcher.first++;
                context.matcher.groups[0] = context.matcher.first;
                ExecuteThread nextThread = new ExecuteThread(context.matcher.first,
                        context.program.getInstructions()[context.program.getStart()]);

                context.threadStack.push(nextThread);
            }
            ExecuteThread currentThread = context.threadStack.peek();
            currentThread.inst.accept(currentThread.sp, instExecutor);
        }
    }

    private void initContext(Matcher matcher, int anchor) {
        this.context.matched = false;
        this.context.matcher = matcher;
        this.context.input = matcher.input;
        this.context.program = matcher.pattern.getProgram();
        this.context.acceptMode = anchor;
    }

    private void clearContext() {
        this.context.threadStack.clear();
        this.context.matched = false;
        this.context.matcher = null;
        this.context.input = null;
        this.context.program = null;
        this.context.acceptMode = NOANCHOR;
    }

    private void advance(int sp, Instruction instruction, int advance) {
        ExecuteThread thread = context.threadStack.pop();
        for (int next : instruction.getNext()) {
            Instruction nextInst = context.program.getInstructions()[next];
            context.threadStack.push(new ExecuteThread(sp + advance, nextInst));
        }
    }

    private void dead() {
        context.threadStack.pop();
    }

    private boolean isOutOfInput(int sp) {
        return context.input.isEnd(sp);
    }

    class ExecuteContext {
        Matcher matcher;
        InputAdapter input;
        Program program;
        int acceptMode = NOANCHOR;
        boolean matched = false;
        Deque<ExecuteThread> threadStack = new LinkedList<ExecuteThread>();
    }

    class ExecuteThread {
        int sp;
        Instruction inst;

        ExecuteThread(int sp, Instruction inst) {
            this.sp = sp;
            this.inst = inst;
        }

        @Override
        public String toString() {
            return "ExecuteThread{" +
                    "sp=" + sp +
                    ", inst=" + inst +
                    '}';
        }
    }

    class InstExecutor implements IExecutor {

        @Override
        public void execute(int sp, EpsilonInstruction instruction) {
            advance(sp, instruction, 0);
        }

        @Override
        public void execute(int sp, BranchInstruction instruction) {
            advance(sp, instruction, 0);
        }

        @Override
        public void execute(int sp, ClosureInstruction instruction) {
            advance(sp, instruction, 0);
        }

        @Override
        public void execute(int sp, RuneInstruction instruction) {
            int[] operands = instruction.getOperands();
            for (int i = 0; i < operands.length; i++) {
                if (isOutOfInput(sp+i) || operands[i] != context.input.get(sp+i)) {
                    dead();
                    return;
                }
            }
            advance(sp, instruction, operands.length);
        }

        @Override
        public void execute(int sp, CharClassInstruction instruction) {
            @SuppressWarnings("unchecked")
            Set<Integer> charClass = (Set<Integer>) instruction.getAttachment();
            if (isOutOfInput(sp) || !charClass.contains(context.input.get(sp))) {
                dead();
                return;
            }
            advance(sp, instruction, 1);
        }

        @Override
        public void execute(int sp, CaptureStartInstruction instruction) {
            int captureIndex = instruction.getOperands()[0];
            switch (instruction.getType()) {
                case NAME:
                    context.matcher.groups[captureIndex * 2] = sp;
                    context.matcher.namedGroups.put(instruction.getName(), captureIndex);
                    break;
                case NORMAL:
                    context.matcher.groups[captureIndex * 2] = sp;
                    break;
                case NONE:
                    break;
            }
            advance(sp, instruction, 0);
        }

        @Override
        public void execute(int sp, CaptureEndInstruction instruction) {
            int captureIndex = instruction.getOperands()[0];
            switch (instruction.getType()) {
                case NAME:
                    context.matcher.groups[captureIndex * 2 + 1] = sp;
                    context.matcher.namedGroups.put(instruction.getName(), captureIndex);
                    break;
                case NORMAL:
                    context.matcher.groups[captureIndex * 2 + 1] = sp;
                    break;
                case NONE:
                    break;
            }
            advance(sp, instruction, 0);
        }

        @Override
        public void execute(int sp, AssertionInstruction instruction) {

        }

        @Override
        public void execute(int sp, BoundaryInstruction instruction) {
            switch (instruction.getType()) {
                case BEGIN_LINE:
                    if (context.input.start() != sp) {
                        dead();
                        return;
                    }
                    break;
                case END_LINE_BOUNDARY:
                    if (context.input.end() != sp) {
                        dead();
                        return;
                    }
                    break;
            }
            advance(sp, instruction, 0);
        }

        @Override
        public void execute(int sp, EndInstruction instruction) {
        }

        @Override
        public void execute(int sp, MatchInstruction instruction) {
            switch (context.acceptMode) {
                case NOANCHOR:
                    context.matched = true;
                    context.matcher.last = sp - 1;
                    context.matcher.groups[1] = sp;
                    break;
                case ENDANCHOR:
                    if (context.input.isEnd(sp)) {
                        context.matched = true;
                        context.matcher.last = sp - 1;
                        context.matcher.groups[1] = sp;
                    }else {
                        dead();
                    }
                    break;
            }
        }

    }
}

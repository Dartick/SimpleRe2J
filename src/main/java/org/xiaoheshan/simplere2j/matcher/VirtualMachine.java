package org.xiaoheshan.simplere2j.matcher;

import org.xiaoheshan.simplere2j.instruction.*;
import org.xiaoheshan.simplere2j.program.Program;

import java.util.*;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public class VirtualMachine {

    private boolean matched;
    private ExecuteContext context = new ExecuteContext();
    private InstExecutor instExecutor = new InstExecutor();

    private Matcher matcher;
    private InputAdapter input;
    private Program program;

    VirtualMachine() {
        this.matched = false;
    }

    public boolean execute(Matcher matcher) {

        this.matcher = matcher;
        this.input = matcher.input;
        this.program = matcher.pattern.getProgram();
        matcher.first = matcher.last + 1;
        matcher.groups[0] = matcher.first;
        matcher.groupCount++;
        ExecuteThread thread = new ExecuteThread(matcher.first , program.getInstructions()[program.getStart()]);

        context.threadStack.push(thread);

        while (!context.threadStack.isEmpty()) {
            if (matched) {
                return matched;
            }
            ExecuteThread currentThread = context.threadStack.peek();
            currentThread.inst.accept(instExecutor);
        }

        boolean result = matched;
        clear();

        return result;
    }

    private void clear() {
        this.context.threadStack.clear();
        this.context.instStack.clear();
        this.context.captureHits.clear();
        this.matched = false;
        this.matcher = null;
        this.input = null;
        this.program = null;
    }

    private void advance(Instruction instruction, int advance) {
        ExecuteThread thread = context.threadStack.pop();
        for (int next : instruction.getNext()) {
            Instruction nextInst = program.getInstructions()[next];
            context.threadStack.push(new ExecuteThread(thread.pc + advance, nextInst));
        }
    }

    private boolean isOutOfInput(int pc) {
        return input.isEnd(pc);
    }

    private void failure() {
        context.threadStack.pop();
    }

    private ExecuteThread currentThread() {
        return context.threadStack.peek();
    }

    class ExecuteContext {
        Deque<Instruction> instStack = new LinkedList<Instruction>();
        Deque<ExecuteThread> threadStack = new LinkedList<ExecuteThread>();
        Map<CaptureInstruction, CaptureHolder> captureHits = new HashMap<CaptureInstruction, CaptureHolder>();
    }

    class CaptureHolder {
        int from;
        int captureIndex;

        CaptureHolder(int from, int captureIndex) {
            this.from = from;
            this.captureIndex = captureIndex;
        }
    }

    class ExecuteThread {
        int pc;
        Instruction inst;

        ExecuteThread(int pc, Instruction inst) {
            this.pc = pc;
            this.inst = inst;
        }

        @Override
        public String toString() {
            return "ExecuteThread{" +
                    "pc=" + pc +
                    ", inst=" + inst +
                    '}';
        }
    }



    class InstExecutor implements IExecutor {

        @Override
        public void execute(EpsilonInstruction instruction) {
            advance(instruction, 0);
        }

        @Override
        public void execute(BranchInstruction instruction) {
            advance(instruction, 0);
        }

        @Override
        public void execute(ClosureInstruction instruction) {
            advance(instruction, 0);
        }

        @Override
        public void execute(RuneInstruction instruction) {
            int pc = currentThread().pc;
            int[] operands = instruction.getOperands();
            for (int i = 0; i < operands.length; i++) {
                if (isOutOfInput(pc+i) || operands[i] != input.get(pc+i)) {
                    failure();
                    return;
                }
            }
            advance(instruction, operands.length);
        }

        @Override
        public void execute(CharClassInstruction instruction) {
            int pc = currentThread().pc;
            @SuppressWarnings("unchecked")
            Set<Integer> charClass = (Set<Integer>) instruction.getAttachment();
            if (isOutOfInput(pc) || !charClass.contains(input.get(pc))) {
                failure();
                return;
            }
            advance(instruction, 1);
        }

        @Override
        public void execute(CaptureInstruction instruction) {
            int pc = currentThread().pc;
            CaptureHolder holder = context.captureHits.get(instruction);
            if (holder == null) {
                context.captureHits.put(instruction, new CaptureHolder(pc, matcher.groupCount));
                matcher.groupCount++;
            }
            else {
                holder.from = pc;
            }
            context.instStack.push(instruction);
            advance(instruction, 0);
        }

        @Override
        public void execute(AssertionInstruction instruction) {
        }

        @Override
        public void execute(BoundaryInstruction instruction) {
        }

        @Override
        public void execute(EndInstruction instruction) {
            int pc = currentThread().pc;
            if (context.instStack.isEmpty()) {
                advance(instruction, 0);
                return;
            }
            Instruction lastInst = context.instStack.peek();
            if (lastInst instanceof CaptureInstruction) {
                CaptureInstruction captureInstruction = (CaptureInstruction) lastInst;
                CaptureHolder holder = context.captureHits.get(captureInstruction);
                switch (captureInstruction.getType()) {
                    case NORMAL:
                        matcher.groups[holder.captureIndex *2] = holder.from;
                        matcher.groups[holder.captureIndex *2 + 1] = pc;
                        break;
                    case NAME:
                        matcher.groups[holder.captureIndex *2] = holder.from;
                        matcher.groups[holder.captureIndex *2 + 1] = pc;
                        matcher.namedGroups.put(captureInstruction.getName(), holder.from);
                    case NONE:
                        break;
                }
                context.instStack.pop();
            }
            advance(instruction, 0);
        }

        @Override
        public void execute(MatchInstruction instruction) {
            matched = true;
            matcher.last = currentThread().pc - 1;
            matcher.groups[1] = currentThread().pc;
        }

    }
}

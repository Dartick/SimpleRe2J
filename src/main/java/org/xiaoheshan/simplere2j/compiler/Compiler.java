package org.xiaoheshan.simplere2j.compiler;

import org.xiaoheshan.simplere2j.ast.AstNode;
import org.xiaoheshan.simplere2j.ast.AstVisitor;
import org.xiaoheshan.simplere2j.ast.VisitorHandler;
import org.xiaoheshan.simplere2j.ast.composite.*;
import org.xiaoheshan.simplere2j.ast.leaf.BoundaryNode;
import org.xiaoheshan.simplere2j.ast.leaf.CharClassNode;
import org.xiaoheshan.simplere2j.ast.leaf.LiteralNode;
import org.xiaoheshan.simplere2j.instruction.Instruction;
import org.xiaoheshan.simplere2j.instruction.Instructions;
import org.xiaoheshan.simplere2j.program.Program;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author _Chf
 * @date 2017-09-23
 */
public class Compiler implements AstVisitor {

    private List<Instruction> instructions;
    private Deque<List<InstructionHolder>> holders;
    private int captureCount;

    private VisitorHandler orHandler = new BaseVisitorHandler() {
        @Override
        public void visitAfter(AstNode node) {
            InstructionHolder result = new InstructionHolder();
            Instruction entranceInst = Instructions.newBranch();
            Instruction exitInst = Instructions.newEnd();

            List<InstructionHolder> holderList = holders.pop();

            int[] next = new int[holderList.size()];
            int entrance = appendInstruction(entranceInst);
            int exit = appendInstruction(exitInst);

            for (int i = 0; i < holderList.size(); i++) {
                next[i] = holderList.get(i).entrance;
                Instruction instruction = instructions.get(holderList.get(i).exit);
                instruction.setNext(new int[]{exit});
            }

            entranceInst.setNext(next);

            result.entrance = entrance;
            result.exit = exit;

            holders.peek().add(result);
        }
    };

    private VisitorHandler andHandler = new BaseVisitorHandler() {
        @Override
        public void visitAfter(AstNode node) {
            List<InstructionHolder> holderList = holders.pop();
            InstructionHolder result = holderList.get(0);
            InstructionHolder parent = result;
            for (int i = 1; i < holderList.size(); i++) {
                Instruction instruction = instructions.get(parent.exit);
                instruction.setNext(new int[]{holderList.get(i).entrance});
                parent = holderList.get(i);
            }
            result.exit = holderList.get(holderList.size() - 1).exit;

            holders.peek().add(result);
        }
    };

    private VisitorHandler captureHandler = new BaseVisitorHandler() {
        @Override
        public void visitAfter(AstNode node) {
            CaptureNode captureNode = (CaptureNode) node;
            if (captureNode.getType() != CaptureNode.Type.NONE) {
                captureCount++;
            }
            Instruction entranceInst = Instructions.newCaptureStart(captureNode.getName(), captureCount, captureNode.getType());
            int entrance = appendInstruction(entranceInst);
            Instruction endInst = Instructions.newCaptureEnd(captureNode.getName(), captureCount, captureNode.getType());
            int exit = appendInstruction(endInst);

            InstructionHolder holder = holders.pop().get(0);

            entranceInst.setNext(new int[]{holder.entrance});

            endInst.setNext(new int[]{holder.exit});

            Instruction instruction = instructions.get(holder.exit);
            instruction.setNext(new int[]{exit});

            holder.entrance = entrance;
            holder.exit = exit;

            holders.peek().add(holder);
        }
    };

    private VisitorHandler assertHandler = new BaseVisitorHandler() {
        @Override
        public void visitAfter(AstNode node) {
            AssertNode assertNode = (AssertNode) node;
            Instruction entranceInst = Instructions.newAssertion(assertNode.getType());

            InstructionHolder holder = holders.pop().get(0);

            int entrance = appendInstruction(entranceInst);
            int exit = appendInstruction(Instructions.newEnd());

            entranceInst.setNext(new int[]{holder.entrance});

            Instruction instruction = instructions.get(holder.exit);
            instruction.setNext(new int[]{exit});

            holder.entrance = entrance;
            holder.exit = exit;

            holders.peek().add(holder);
        }
    };

    private VisitorHandler starHandler = new BaseVisitorHandler() {
        @Override
        public void visitAfter(AstNode node) {
            StarNode starNode = (StarNode) node;
            InstructionHolder holder = holders.pop().get(0);
            Instruction instruction = instructions.get(holder.exit);
            Instruction entranceInst = Instructions.newClosure();
            int entrance = appendInstruction(entranceInst);
            Instruction exitInst = Instructions.newEpsilon();
            int exit = appendInstruction(exitInst);

            switch (starNode.getType()) {
                case POSSESSIVE:
                    entranceInst.setNext(new int[]{holder.entrance});
                    break;
                case RELUCTANT:
                    entranceInst.setNext(new int[]{holder.entrance, exit});
                    break;
                case GREEDY:
                default:
                    entranceInst.setNext(new int[]{exit, holder.entrance});
            }
            instruction.setNext(new int[]{entrance});
            holder.entrance = entrance;
            holder.exit = exit;

            holders.peek().add(holder);
        }
    };

    private VisitorHandler questionHandler = new BaseVisitorHandler() {
        @Override
        public void visitAfter(AstNode node) {
            QuestionNode questionNode = (QuestionNode) node;
            Instruction entranceInst = Instructions.newBranch();
            int entrance = appendInstruction(entranceInst);
            Instruction exitInst = Instructions.newEnd();
            int exit = appendInstruction(exitInst);

            InstructionHolder holder = holders.pop().get(0);

            switch (questionNode.getType()) {
                case POSSESSIVE:
                    entranceInst.setNext(new int[]{exit});
                    break;
                case RELUCTANT:
                    entranceInst.setNext(new int[]{holder.entrance, exit});
                    break;
                case GREEDY:
                default:
                    entranceInst.setNext(new int[]{exit, holder.entrance});
            }

            Instruction instruction = instructions.get(holder.entrance);
            instruction.setNext(new int[]{exit});

            holder.entrance = entrance;
            holder.exit = exit;

            holders.peek().add(holder);
        }
    };

    public Program compile(AstNode root, int mode) {

        instructions = new ArrayList<Instruction>();
        holders = new LinkedList<List<InstructionHolder>>();
        holders.push(new ArrayList<InstructionHolder>());

        InstructionHolder holder = compileInternal(root, mode);
        Program program = new Program(holder.entrance, holder.exit, captureCount,
                instructions.toArray(new Instruction[instructions.size()]));

        instructions = null;
        holders = null;
        captureCount = 0;

        return program;
    }

    private void clear() {
        instructions = null;
        holders = null;
        captureCount = 0;
    }

    private InstructionHolder compileInternal(AstNode root, int mode) {
        root.accept(this);

        InstructionHolder holder = holders.pop().get(0);

        Instruction matchInst = Instructions.newMatch();
        int match = appendInstruction(matchInst);

        Instruction instruction = instructions.get(holder.exit);

        instruction.setNext(new int[]{match});

        holder.exit = match;

        return holder;
    }

    @Override
    public void visit(BoundaryNode node) {
        requireHoldersNotNull();
        Instruction boundaryInst = Instructions.newBoundary(node.getType());
        int index = appendInstruction(boundaryInst);
        holders.peek().add(new InstructionHolder(index, index));
    }

    @Override
    public void visit(CharClassNode node) {
        requireHoldersNotNull();
        Instruction charClassInst = Instructions.newCharClass(node.getValue());
        int index = appendInstruction(charClassInst);
        holders.peek().add(new InstructionHolder(index, index));
    }

    @Override
    public void visit(LiteralNode node) {
        requireHoldersNotNull();
        Instruction instruction = Instructions.newRune(node.getValue());
        int index = appendInstruction(instruction);
        holders.peek().add(new InstructionHolder(index, index));
    }

    @Override
    public VisitorHandler visit(OrNode node) {
        return orHandler;
    }

    @Override
    public VisitorHandler visit(AndNode node) {
        return andHandler;
    }

    @Override
    public VisitorHandler visit(CaptureNode node) {
        return captureHandler;
    }

    @Override
    public VisitorHandler visit(AssertNode node) {
        return assertHandler;
    }

    @Override
    public VisitorHandler visit(StarNode node) {
        return starHandler;
    }

    @Override
    public VisitorHandler visit(QuestionNode node) {
        return questionHandler;
    }

    private int appendInstruction(Instruction instruction) {
        int index = instructions.size();
        instructions.add(instruction);
        return index;
    }

    private void requireHoldersNotNull() {
        if (holders.peek() == null) {
            holders.push(new ArrayList<InstructionHolder>());
        }
    }

    class InstructionHolder {
        int entrance;
        int exit;
        InstructionHolder() {
        }
        InstructionHolder(int entrance, int exit) {
            this.entrance = entrance;
            this.exit = exit;
        }
    }

    abstract class BaseVisitorHandler implements VisitorHandler {
        @Override
        public void visitBefore(AstNode node) {
            holders.push(new ArrayList<InstructionHolder>());
        }
    }

    @Override
    public VisitorHandler visit(ClosureNode node) {
        return null;
    }

    @Override
    public VisitorHandler visit(PlusNode node) {
        return null;
    }
}

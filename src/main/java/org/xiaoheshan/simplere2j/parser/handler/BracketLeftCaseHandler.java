package org.xiaoheshan.simplere2j.parser.handler;

import org.xiaoheshan.simplere2j.ast.AstNode;
import org.xiaoheshan.simplere2j.ast.leaf.CharClassNode;
import org.xiaoheshan.simplere2j.scanner.ScanSource;
import org.xiaoheshan.simplere2j.utils.ArrayUtil;

import java.util.*;

/**
 * @author _Chf
 * @date 2017-09-20
 */
public class BracketLeftCaseHandler extends AbstractCaseHandler {

    @Override
    public char[] matchedCase() {
        return new char[]{'['};
    }

    @Override
    public void handle(ScanSource source, Deque<AstNode> stack, int mode) {
        Deque<Container> containers = new LinkedList<Container>();
        while (!source.isEnd()) {
            for (Special special : Special.values()) {
                if (special.is(source.peek())) {
                    special.doHandle(source, containers, mode);
                    break;
                }
            }
            if (containers.peek().finished) {
                break;
            }
        }
        Container top = containers.pop();
        stack.push(new CharClassNode(top.contains));
    }

    private static class Container {

        boolean un;
        boolean finished;
        boolean intersection;
        Set<Integer> contains;

        Container() {
            contains = new HashSet<Integer>();
        }

        void add(int i) {
            if (un) {
                contains.remove(i);
            }
            else {
                contains.add(i);
            }
        }

        void combine(Container that) {
            if (intersection) {
                contains.retainAll(that.contains);
            }
            else {
                contains.addAll(that.contains);
            }
        }
    }

    private enum Special {

        BACKSLASH('\\') {
            @Override
            void doHandle(ScanSource source, Deque<Container> containers, int mode) {
                source.skip();
                // custom char classes
                for (Special special : Special.values()) {
                    if (special.is(source.peek())) {
                        containers.peek().add(source.poll());
                        break;
                    }
                }
            }
        },
        CARET('^') {
            @Override
            void doHandle(ScanSource source, Deque<Container> containers, int mode) {
                containers.peek().add(source.poll());
            }
        },
        MINUS('-') {
            @Override
            void doHandle(ScanSource source, Deque<Container> containers, int mode) {
                containers.peek().add(source.poll());
            }
        },
        AMPERSAND('&') {
            @Override
            void doHandle(ScanSource source, Deque<Container> containers, int mode) {
                if (source.hasNext() && source.next() == '&') {
                    source.skip(2);
                    containers.peek().intersection = true;
                    return;
                }
                containers.peek().add(source.poll());
            }
        },
        BRACKET_LEFT('[') {
            @Override
            void doHandle(ScanSource source, Deque<Container> containers, int mode) {
                if (source.hasNext() && source.next() == '^') {
                    Container container = new Container();
                    container.un = true;
                    Collections.addAll(container.contains, ArrayUtil.supportChars());
                    source.skip(2);
                    containers.push(container);
                    return;
                }
                source.skip();
                containers.push(new Container());
            }
        },
        BRACKET_RIGHT(']') {
            @Override
            void doHandle(ScanSource source, Deque<Container> containers, int mode) {
                source.skip();
                if (containers.size() >= 2) {
                    Container top = containers.pop();
                    containers.peek().combine(top);
                }
                else {
                    containers.peek().finished = true;
                }
            }
        },
        DEFAULT('*') {
            @Override
            boolean is(char that) {
                return true;
            }
            @Override
            void doHandle(ScanSource source, Deque<Container> containers, int mode) {
                if (source.hasNext() &&
                        source.next() == '-' &&
                        source.get(2) != ']') {
                    char min = source.peek();
                    char max = source.get(2);
                    if (min > max) {
                        // throw error
                    }
                    else {
                        for (char i = min; i <= max; i++) {
                            containers.peek().add(i);
                        }
                    }
                    source.skip(3);
                }
                else {
                    containers.peek().add(source.poll());
                }
            }
        }
        ;
        private char name;

        Special(char name) {
            this.name = name;
        }

        boolean is(char that) {
            return name == that;
        }

        abstract void doHandle(ScanSource source, Deque<Container> containers, int mode);
    }
}

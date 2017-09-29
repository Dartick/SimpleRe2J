package org.xiaoheshan.simplere2j.ast.composite;

import org.xiaoheshan.simplere2j.ast.AbstractCompositeNode;
import org.xiaoheshan.simplere2j.ast.AstNode;
import org.xiaoheshan.simplere2j.ast.AstVisitor;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public class ClosureNode extends AbstractCompositeNode {

    private int min;
    private int max;
    private Type type;

    public ClosureNode(int min, int max, Type type, AstNode[] astNodes) {
        super(astNodes);
        this.min = min;
        this.max = max;
        this.type = type;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public Type getType() {
        return type;
    }

    @Override
    protected String prefixString() {
        return "{" + min + ( max == 0 ? "" : ("," + (max == -1 ? "" : max ))) + "}" + type.name;
    }

    @Override
    protected String middleString() {
        return "";
    }

    @Override
    protected String suffixString() {
        return "";
    }

    @Override
    protected void acceptBefore(AstVisitor visitor) {
        visitor.visit(this).visitBefore(this);
    }

    @Override
    protected void acceptAfter(AstVisitor visitor) {
        visitor.visit(this).visitAfter(this);
    }

    public enum Type {
        RELUCTANT('?'),
        POSSESSIVE('+'),
        GREEDY('*') {
            @Override
            public String getName() {
                return "";
            }
        }
        ;

        private char name;

        Type(char name) {
            this.name = name;
        }

        public String getName() {
            return String.valueOf(name);
        }

        public static Type getType(char ch) {
            for (Type type : Type.values()) {
                if (type.name == ch) {
                    return type;
                }
            }
            return GREEDY;
        }
    }

}

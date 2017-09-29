package org.xiaoheshan.simplere2j.ast.composite;

import org.xiaoheshan.simplere2j.ast.AbstractCompositeNode;
import org.xiaoheshan.simplere2j.ast.AstNode;
import org.xiaoheshan.simplere2j.ast.AstVisitor;
import org.xiaoheshan.simplere2j.ast.CaptureType;

/**
 * @author _Chf
 * @date 2017-09-27
 */
public class CaptureNode extends AbstractCompositeNode {

    private String name;
    private Type type;

    public CaptureNode(String name, Type type, AstNode[] astNodes) {
        super(astNodes);
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    protected String prefixString() {
        return type.prefix + (type.isNameType() ? (name + ">") : "");
    }

    @Override
    protected String middleString() {
        return "";
    }

    @Override
    protected String suffixString() {
        return ")";
    }

    @Override
    protected void acceptBefore(AstVisitor visitor) {
        visitor.visit(this).visitBefore(this);
    }

    @Override
    protected void acceptAfter(AstVisitor visitor) {
        visitor.visit(this).visitAfter(this);
    }

    public enum Type implements CaptureType {
        NAME("(?<") {
            @Override
            public boolean isNameType() {
                return true;
            }
        },
        NONE("(?:"),
        DEFAULT("(") {
            @Override
            public boolean is(String that) {
                return true;
            }
        };

        private String prefix;

        Type(String prefix) {
            this.prefix = prefix;
        }


        @Override
        public boolean is(String that) {
            return prefix.equals(that);
        }

        @Override
        public boolean isNameType() {
            return false;
        }

        @Override
        public int getPrefixLength() {
            return prefix.length();
        }
    }
}

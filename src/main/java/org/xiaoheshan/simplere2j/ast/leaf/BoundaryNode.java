package org.xiaoheshan.simplere2j.ast.leaf;

import org.xiaoheshan.simplere2j.ast.AbstractLeafAstNode;
import org.xiaoheshan.simplere2j.ast.AstVisitor;

/**
 * @author _Chf
 * @date 2017-09-24
 */
public class BoundaryNode extends AbstractLeafAstNode<Void> {

    private Type type;

    public BoundaryNode(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public Void getValue() {
        return null;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }

    public enum Type {
        BEGIN_LINE("^"),
        END_LINE("$")
        ;

        private String name;

        Type(String name) {
            this.name = name;
        }
    }



}

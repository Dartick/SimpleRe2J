package org.xiaoheshan.simplere2j.ast.leaf;

import org.xiaoheshan.simplere2j.ast.AbstractLeafAstNode;
import org.xiaoheshan.simplere2j.ast.AstVisitor;
import org.xiaoheshan.simplere2j.utils.ArrayUtil;

/**
 * @author _Chf
 * @date 2017-09-19
 */
public class LiteralNode extends AbstractLeafAstNode<int[]> {

    private String value;

    public LiteralNode(String value) {
        this.value = value;
    }

    @Override
    public int[] getValue() {
        return ArrayUtil.makeIntArray(value.toCharArray());
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return value;
    }
}

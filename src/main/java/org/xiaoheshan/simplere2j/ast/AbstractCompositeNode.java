package org.xiaoheshan.simplere2j.ast;

/**
 * @author _Chf
 * @date 2017-09-19
 */
public abstract class AbstractCompositeNode implements AstNode<Void> {

    private AstNode[] astNodes;

    public AbstractCompositeNode(AstNode[] astNodes) {
        this.astNodes = astNodes;
    }

    @Override
    public AstNode[] getChildren() {
        return astNodes;
    }

    @Override
    public final Void getValue() {
        throw new UnsupportedOperationException("this is a composite node");
    }

    @Override
    public final boolean isLeafNode() {
        return false;
    }

    @Override
    public void accept(AstVisitor visitor) {
        this.acceptBefore(visitor);
        for (AstNode node : astNodes) {
            node.accept(visitor);
        }
        this.acceptAfter(visitor);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(prefixString());
        for (AstNode node : astNodes) {
            builder.append(node).append(middleString());
        }
        if (!"".equals(middleString())) {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append(suffixString());
        return builder.toString();
    }

    protected abstract void acceptBefore(AstVisitor visitor);
    protected abstract void acceptAfter(AstVisitor visitor);
    protected abstract String prefixString();
    protected abstract String middleString();
    protected abstract String suffixString();

}

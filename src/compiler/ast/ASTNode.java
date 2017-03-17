package compiler.ast;

public abstract class ASTNode
{
    abstract void accept(ASTNodeVisitor v);
}

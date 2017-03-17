package compiler.ast;

public class ArgumentList extends ASTNode
{
    public Expr head;
    public ArgumentList next;

    public ArgumentList(Expr e)
    {
        head = e;
        next = null;
    }

    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

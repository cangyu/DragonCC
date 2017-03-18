package compiler.ast;

public class ArgumentList extends ASTNode
{
    public Expr head;
    public ArgumentList next;

    public ArgumentList(Expr _e, ArgumentList _n)
    {
        head = _e;
        next = _n;
    }

    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

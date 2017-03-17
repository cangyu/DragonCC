package compiler.ast;

public class InitializerList extends ASTNode
{
    public Initializer head;
    public InitializerList next;

    public InitializerList(Initializer _i)
    {
        head = _i;
        next = null;
    }

    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

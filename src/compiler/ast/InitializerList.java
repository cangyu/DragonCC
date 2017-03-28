package compiler.ast;

public class InitializerList extends ASTNode
{
    public Initializer head;
    public InitializerList next;

    public InitializerList(Initializer _i, InitializerList _n)
    {
        head = _i;
        next = _n;
    }

    public void accept(ASTNodeVisitor v) throws Exception
    {
        v.visit(this);
    }
}

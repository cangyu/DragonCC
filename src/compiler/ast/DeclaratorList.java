package compiler.ast;

public class DeclaratorList extends ASTNode
{
    public Declarator head;
    public DeclaratorList next;

    public DeclaratorList(Declarator _d)
    {
        head = _d;
        next = null;
    }

    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

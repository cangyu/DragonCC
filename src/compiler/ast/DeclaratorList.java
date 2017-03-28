package compiler.ast;

public class DeclaratorList extends ASTNode
{
    public Declarator head;
    public DeclaratorList next;

    public DeclaratorList(Declarator _d, DeclaratorList _n)
    {
        head = _d;
        next = _n;
    }

    public void accept(ASTNodeVisitor v) throws Exception
    {
        v.visit(this);
    }
}

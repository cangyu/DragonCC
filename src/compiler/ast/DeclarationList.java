package compiler.ast;

public class DeclarationList extends ASTNode
{
    public Declaration head;
    public DeclarationList next;

    public DeclarationList(Declaration _d)
    {
        head = _d;
        next = null;
    }

    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

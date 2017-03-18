package compiler.ast;

public class NonInitDeclarationList extends ASTNode
{
    public NonInitDeclaration head;
    public NonInitDeclarationList next;

    public NonInitDeclarationList(NonInitDeclaration _nid, NonInitDeclarationList _n)
    {
        head = _nid;
        next = _n;
    }

    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

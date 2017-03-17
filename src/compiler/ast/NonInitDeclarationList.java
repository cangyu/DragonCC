package compiler.ast;

public class NonInitDeclarationList extends ASTNode
{
    public NonInitDeclaration head;
    public NonInitDeclarationList next;

    public NonInitDeclarationList(NonInitDeclaration _nid)
    {
        head = _nid;
        next = null;
    }

    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

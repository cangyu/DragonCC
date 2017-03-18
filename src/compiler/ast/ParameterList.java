package compiler.ast;

public class ParameterList extends ASTNode
{
    public PlainDeclaration head;
    public ParameterList next;

    public ParameterList(PlainDeclaration _pd, ParameterList _n)
    {
        head = _pd;
        next = _n;
    }

    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

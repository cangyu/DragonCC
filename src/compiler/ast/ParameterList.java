package compiler.ast;

public class ParameterList extends ASTNode
{
    public PlainDeclaration head;
    public ParameterList next;

    public ParameterList(PlainDeclaration _pd)
    {
        head = _pd;
        next = null;
    }

    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

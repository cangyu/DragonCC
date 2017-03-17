package compiler.ast;

public class FuncDeclarator extends Declarator
{
    public PlainDeclarator plain_declarator;
    public ParameterList param;
    
    public FuncDeclarator(PlainDeclarator _pd, ParameterList _pl)
    {
        plain_declarator = _pd;
        param = _pl;
    }

    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

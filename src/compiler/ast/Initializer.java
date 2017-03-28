package compiler.ast;

public class Initializer extends ASTNode
{
    public Expr expr;
    public InitializerList initializer_list;

    public Initializer(Expr _e, InitializerList _is)
    {
        expr = _e;
        initializer_list = _is;
    }

    public void accept(ASTNodeVisitor v) throws Exception
    {
        v.visit(this);
    }
}

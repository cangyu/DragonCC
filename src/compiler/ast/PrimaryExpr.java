package compiler.ast;

public class PrimaryExpr extends Expr
{
    public static enum ElemType
    {
        ID, STRING, CHAR, INT
    };

    public ElemType elem_type;
    public Object elem;

    public PrimaryExpr(ElemType _t, Object _e)
    {
        elem_type = _t;
        elem = _e;
    }

    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

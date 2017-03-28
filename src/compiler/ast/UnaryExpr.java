package compiler.ast;

public class UnaryExpr extends Expr
{
    public static enum Operator
    {
        BIT_AND, STAR, POSITIVE, NEGATIVE, BIT_NOT, NOT, SIZEOF, INC, DEC
    };

    public Operator op;
    public Expr expr;
    public TypeName type_name;

    public UnaryExpr(Operator _t, Expr _e, TypeName _tn)
    {
        op = _t;
        expr = _e;
        type_name = _tn;
    }

    public void accept(ASTNodeVisitor v) throws Exception
    {
        v.visit(this);
    }
}

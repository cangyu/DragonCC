package compiler.ast;

public class PostfixExpr extends Expr
{
    public static enum Operator
    {
        MPAREN, PAREN, DOT, PTR, INC, DEC
    };

    public Expr expr;
    public Operator op;
    public Object param;

    public PostfixExpr(Expr _pexpr, Object _param, Operator _t)
    {
        expr = _pexpr;
        param = _param;
        op = _t;
    }

    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

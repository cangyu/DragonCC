package compiler.ast;

public class BinaryExpr extends Expr
{
    public static enum Operator
    {
        BIT_AND, BIT_XOR, BIT_OR, AND, OR, EQ, NE, LT, GT, LE, GE, SHL, SHR, PLUS, MINUS, TIMES, DIVIDE, MODULE
    };

    public Operator op;
    public Expr left, right;

    public BinaryExpr(Operator _t, Expr _l, Expr _r)
    {
        op = _t;
        left = _l;
        right = _r;
    }

    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

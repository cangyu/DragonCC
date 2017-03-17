package compiler.ast;

public class IterationStmt extends Stmt
{
    public static enum Type
    {
        WHILE, FOR
    };

    public Type iteration_type;
    public Expr init, judge, next;
    public Stmt stmt;

    public IterationStmt(Expr _cond, Stmt _s)
    {
        iteration_type = Type.WHILE;
        judge = _cond;
        stmt = _s;
    }

    public IterationStmt(Expr _t1, Expr _t2, Expr _t3, Stmt _s)
    {
        iteration_type = Type.FOR;
        init = _t1;
        judge = _t2;
        next = _t3;
        stmt = _s;
    }

    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

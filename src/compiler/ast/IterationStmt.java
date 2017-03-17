package compiler.ast;

public class IterationStmt extends Stmt
{
	public static enum Type{WHILE, FOR};
	
	public Type iteration_type;
	public Expr cond;
	public Stmt stmt;
	public Expr init_op, next_op;
	
	public IterationStmt(Expr _cond, Stmt _s)
	{
		iteration_type = Type.WHILE;
		cond = _cond;
		stmt = _s;
	}
	
	public IterationStmt(Expr _t1, Expr _t2, Expr _t3, Stmt _s)
	{
		iteration_type = Type.FOR;
		init_op = _t1;
		cond = _t2;
		next_op = _t3;
		stmt = _s;
	}
	
    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

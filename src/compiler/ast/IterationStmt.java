package compiler.ast;

public class IterationStmt extends Stmt
{
	public static enum IterationType{WHILE, FOR};
	
	public IterationType type;
	public Expr cond;
	public Stmt stmt;
	public Expr init_op, next_op;
	
	public IterationStmt(Expr _cond, Stmt _s)
	{
		type=IterationType.WHILE;
		cond=_cond;
		stmt=_s;
	}
	
	public IterationStmt(Expr _t1, Expr _t2, Expr _t3, Stmt _s)
	{
		type=IterationType.FOR;
		init_op=_t1;
		cond=_t2;
		next_op=_t3;
		stmt=_s;
	}
}

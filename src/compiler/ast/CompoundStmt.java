package compiler.ast;

public class CompoundStmt extends Stmt
{
	public Decls decls;
	public Stmts stmts;
	
	public CompoundStmt(Decls _ds, Stmts _ss)
	{
		decls = _ds;
		stmts = _ss;
	}
}

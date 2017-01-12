package compiler.ast;

public class CompoundStmt 
{
	public Decls decls;
	public Stmts stmts;
	
	public CompoundStmt(Decls _ds, Stmts _ss)
	{
		decls=_ds;
		stmts=_ss;
	}
}

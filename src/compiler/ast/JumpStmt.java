package compiler.ast;

public class JumpStmt extends Stmt
{
	public static enum JumpType{CONTINUE, BREAK, RETURN};
	
	public JumpType type;
	public Expr ans;
	
	public JumpStmt(JumpType _jt)
	{
		type=_jt;
	}
	
	public JumpStmt(Expr _ans)
	{
		type=JumpType.RETURN;
		ans=_ans;
	}
}

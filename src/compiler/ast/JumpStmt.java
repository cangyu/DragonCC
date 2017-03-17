package compiler.ast;

public class JumpStmt extends Stmt
{
	public static enum Type{CONTINUE, BREAK, RETURN};
	
	public Type type;
	public Expr ans;
	
	public JumpStmt(Type _jt, Expr _e)
	{
		type = _jt;
		ans = _e;
	}
	
    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

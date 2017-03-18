package compiler.ast;

public class ExpressionStmt extends Stmt
{
	public Expression e;
	
	public ExpressionStmt(Expression _e)
	{
		e = _e;
	}
	
    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

package compiler.ast;

public class Expression extends Expr
{
	public Expr head;
	public Expression next;
	
	public Expression(Expr _e, Expression _n)
	{
	    head = _e;
	    next = _n;
	}
	
    public void accept(ASTNodeVisitor v) throws Exception
    {
        v.visit(this);
    }
}

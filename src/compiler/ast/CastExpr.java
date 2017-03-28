package compiler.ast;

public class CastExpr extends Expr
{
	public TypeName target_type;
	public Expr expr;
	
	public CastExpr(TypeName _t, Expr _e)
	{
	    target_type = _t;
		expr = _e;
	}
	
    public void accept(ASTNodeVisitor v) throws Exception
    {
        v.visit(this);
    }
}

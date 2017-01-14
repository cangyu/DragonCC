package compiler.ast;

public class CastExpr extends Expr
{
	public TypeName type_name;
	public Expr expr;
	
	public CastExpr(TypeName _t, Expr _e)
	{
		type_name = _t;
		expr = _e;
	}
}

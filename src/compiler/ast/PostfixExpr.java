package compiler.ast;

public class PostfixExpr extends Expr
{
	public static enum Op{MPAREN, PAREN, DOT, PTR, INC, DEC};
	
	public Op operation_type;
	public Object param;
	public Expr expr;
	
	public PostfixExpr(Op _t, Object _param, Expr _pexpr)
	{
		operation_type=_t;
		param=_param;
		expr=_pexpr;
	}
}

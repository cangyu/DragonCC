package compiler.ast;

public class PostfixExpr extends Expr
{
	public static enum Type{MPAREN, PAREN, DOT, PTR, INC, DEC};
	
	public Type operation_type;
	public Object param;
	public Expr expr;
	
	public PostfixExpr(Type _t, Object _param, Expr _pexpr)
	{
		operation_type = _t;
		param = _param;
		expr = _pexpr;
	}
	
	@Override
	public String toString()
	{
		switch(operation_type)
		{
		case MPAREN:
			return expr.toString() + "[" + ((Expr)param).toString() + "]";
		case PAREN:
			return expr.toString() + "(" + (param==null ? "" : ((Expr)param).toString()) + ")";
		case DOT:
			return expr.toString() + "." + (String)param;
		case PTR:
			return expr.toString() + "->" + (String)param;
		case INC:
			return expr.toString() + "++";
		case DEC:
			return expr.toString() + "--";
		default:
			return "";
		}
	}
}

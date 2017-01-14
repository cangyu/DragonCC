package compiler.ast;

public class UnaryExpr extends Expr
{
	public static enum Type{BIT_AND, STAR, POSITIVE, NEGATIVE, BIT_NOT, NOT, SIZEOF, INC, DEC};
	
	public Type operation_type;
	public Expr expr;
	public TypeName type_name;
	
	public UnaryExpr(Type _t, Expr _e)
	{
		operation_type = _t;
		expr = _e;
	}
	
	public UnaryExpr(TypeName _t)
	{
		operation_type = Type.SIZEOF;
		type_name = _t;
	}
}
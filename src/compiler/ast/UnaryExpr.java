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
	
	@Override
	public String toString()
	{
		switch(operation_type)
		{
		case BIT_AND:
			return "&" + expr.toString();
		case STAR:
			return "*" + expr.toString();
		case POSITIVE:
			return "+" + expr.toString();
		case NEGATIVE:
			return "-" + expr.toString();
		case BIT_NOT:
			return "~" + expr.toString();
		case NOT:
			return "!" + expr.toString();
		case SIZEOF:
			return "SIZEOF" + (type_name == null ? expr.toString() : type_name.toString());
		case INC:
			return "++" + expr.toString();
		case DEC:
			return "--" + expr.toString();
		default:
			return "";
		}
	}
}
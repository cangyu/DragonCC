package compiler.ast;

public class BinaryExpr extends Expr
{
	public static enum Type{BIT_AND, BIT_XOR, BIT_OR, AND, OR, EQ, NE, LT, GT, LE, GE, SHL, SHR, PLUS, MINUS, TIMES, DIVIDE, MODULE};
	
	public Type operation_type;
	public Expr left, right;
	
	public BinaryExpr(Type _t, Expr _l, Expr _r)
	{
		operation_type = _t;
		left = _l;
		right = _r;
	}
}

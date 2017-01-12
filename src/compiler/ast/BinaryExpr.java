package compiler.ast;

public class BinaryExpr extends Expr
{
	public static enum Op{EQ, NE, LT, GT, LE, GE, SHL, SHR, PLUS, MINUS, TIMES, DIVIDE, MODULE};
	
	public Op operation_type;
	public BinaryExpr left, right;
	
	public BinaryExpr(Op _t, BinaryExpr _l, BinaryExpr _r)
	{
		operation_type=_t;
		left=_l;
		right=_r;
	}
}

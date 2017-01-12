package compiler.ast;

public class PostfixExpr extends Expr
{
	public static enum Op{MPAREN, PAREN, DOT, PTR, INC, DEC};
	
	public Op operation_type;
	public Expr param;
	
	public PostfixExpr(Op _t, Expr _p, )
	{
		
	}
}

package compiler.ast;

public class AssignmentExpr extends Expr
{
	public static enum Op{ASSIGN, MUL_ASSIGN, DIV_ASSIGN, MOD_ASSIGN, ADD_ASSIGN, SUB_ASSIGN, SHL_ASSIGN, SHR_ASSIGN, AND_ASSIGN, XOR_ASSIGN, OR_ASSIGN};

	public Op operation_type;
	public UnaryExpr left;
	public AssignmentExpr right;
	
	public AssignmentExpr(Op _t, UnaryExpr _l, AssignmentExpr _r)
	{
		operation_type=_t;
		left=_l;
		right=_r;
	}
	
	
}

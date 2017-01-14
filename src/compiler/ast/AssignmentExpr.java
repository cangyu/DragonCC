package compiler.ast;

public class AssignmentExpr extends Expr
{
	public static enum Type{ASSIGN, MUL_ASSIGN, DIV_ASSIGN, MOD_ASSIGN, ADD_ASSIGN, SUB_ASSIGN, SHL_ASSIGN, SHR_ASSIGN, AND_ASSIGN, XOR_ASSIGN, OR_ASSIGN};

	public Type operation_type;
	public Expr left, right;
	
	public AssignmentExpr(Type _t, Expr _l, Expr _r)
	{
		operation_type = _t;
		left = _l;
		right = _r;
	}
	
	
}

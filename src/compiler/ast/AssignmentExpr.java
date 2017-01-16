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
	
	@Override
	public String toString()
	{
		switch(operation_type)
		{
		case ASSIGN:
			return left.toString() + "=" +right.toString();
		case MUL_ASSIGN:
			return left.toString() + "*=" +right.toString();
		case DIV_ASSIGN:
			return left.toString() + "/=" +right.toString();
		case MOD_ASSIGN:
			return left.toString() + "%=" +right.toString();
		case ADD_ASSIGN:
			return left.toString() + "+=" +right.toString();
		case SUB_ASSIGN:
			return left.toString() + "-=" +right.toString();
		case SHL_ASSIGN: 
			return left.toString() + "<<=" +right.toString();
		case SHR_ASSIGN: 
			return left.toString() + ">>=" +right.toString();
		case AND_ASSIGN: 
			return left.toString() + "&=" +right.toString();
		case XOR_ASSIGN: 
			return left.toString() + "^=" +right.toString();
		case OR_ASSIGN: 
			return left.toString() + "|=" +right.toString();
		default:
			return "";
		}
	}
}

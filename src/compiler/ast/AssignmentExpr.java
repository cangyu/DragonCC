package compiler.ast;

public class AssignmentExpr extends Expr
{
	public static enum Operator
	{
		ASSIGN, MUL_ASSIGN, DIV_ASSIGN, MOD_ASSIGN, ADD_ASSIGN, SUB_ASSIGN, SHL_ASSIGN, SHR_ASSIGN, AND_ASSIGN, XOR_ASSIGN, OR_ASSIGN
	};

	public Operator op;
	public Expr left, right;

	public AssignmentExpr(Operator _op, Expr _l, Expr _r)
	{
		op = _op;
		left = _l;
		right = _r;
	}

	public void accept(ASTNodeVisitor v) throws Exception
	{
		v.visit(this);
	}

	public static String getOperator(Operator op)
	{
		switch (op)
		{
		case ASSIGN:
			return "=";
		case MUL_ASSIGN:
			return "*=";
		case DIV_ASSIGN:
			return "/=";
		case MOD_ASSIGN:
			return "%=";
		case ADD_ASSIGN:
			return "+=";
		case SUB_ASSIGN:
			return "-=";
		case SHL_ASSIGN:
			return "<<=";
		case SHR_ASSIGN:
			return ">>=";
		case AND_ASSIGN:
			return "&=";
		case XOR_ASSIGN:
			return "^=";
		case OR_ASSIGN:
			return "|=";
		default:
			return "";
		}
	}

	public String getOperator()
	{
		return getOperator(op);
	}
}

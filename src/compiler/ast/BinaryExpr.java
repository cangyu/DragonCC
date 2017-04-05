package compiler.ast;

import compiler.ast.AssignmentExpr.Operator;

public class BinaryExpr extends Expr
{
	public static enum Operator
	{
		BIT_AND, BIT_XOR, BIT_OR, AND, OR, EQ, NE, LT, GT, LE, GE, SHL, SHR, PLUS, MINUS, TIMES, DIVIDE, MODULE
	};

	public Operator op;
	public Expr left, right;

	public BinaryExpr(Operator _t, Expr _l, Expr _r)
	{
		op = _t;
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
		case BIT_AND:
			return "&";
		case BIT_XOR:
			return "^";
		case BIT_OR:
			return "|";
		case AND:
			return "&&";
		case OR:
			return "||";
		case EQ:
			return "==";
		case NE:
			return "!=";
		case LT:
			return "<";
		case GT:
			return ">";
		case LE:
			return "<=";
		case GE:
			return ">=";
		case SHL:
			return "<<";
		case SHR:
			return ">>";
		case PLUS:
			return "+";
		case MINUS:
			return "-";
		case TIMES:
			return "*";
		case DIVIDE:
			return "/";
		case MODULE:
			return "%";
		default:
			return "";
		}
	}

	public String getOperator()
	{
		return getOperator(op);
	}
}

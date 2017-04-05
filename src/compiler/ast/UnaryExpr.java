package compiler.ast;

import compiler.ast.AssignmentExpr.Operator;

public class UnaryExpr extends Expr
{
	public static enum Operator
	{
		BIT_AND, STAR, POSITIVE, NEGATIVE, BIT_NOT, NOT, SIZEOF, INC, DEC
	};

	public Operator op;
	public Expr expr;
	public TypeName type_name;

	public UnaryExpr(Operator _t, Expr _e, TypeName _tn)
	{
		op = _t;
		expr = _e;
		type_name = _tn;
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
		case STAR:
			return "*";
		case POSITIVE:
			return "+";
		case NEGATIVE:
			return "-";
		case BIT_NOT:
			return "~";
		case NOT:
			return "!";
		case SIZEOF:
			return "sizeof";
		case INC:
			return "++";
		case DEC:
			return "--";
		default:
			return "";
		}
	}

	public String getOperator()
	{
		return getOperator(op);
	}
}

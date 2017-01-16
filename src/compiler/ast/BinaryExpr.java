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
	
	@Override
	public String toString()
	{
		switch(operation_type)
		{
		case BIT_AND:
			return left.toString() + "&" +right.toString();
		case BIT_XOR:
			return left.toString() + "^" +right.toString();
		case BIT_OR:
			return left.toString() + "|" +right.toString();
		case AND:
			return left.toString() + "&&" +right.toString();
		case OR:
			return left.toString() + "||" +right.toString();
		case EQ:
			return left.toString() + "==" +right.toString();
		case NE: 
			return left.toString() + "!=" +right.toString();
		case LT: 
			return left.toString() + "<" +right.toString();
		case GT: 
			return left.toString() + ">" +right.toString();
		case LE: 
			return left.toString() + "<=" +right.toString();
		case GE: 
			return left.toString() + ">=" +right.toString();
		case SHL: 
			return left.toString() + "<<" +right.toString();
		case SHR: 
			return left.toString() + ">>" +right.toString();
		case PLUS:
			return left.toString() + "+" +right.toString();
		case MINUS: 
			return left.toString() + "-" +right.toString();
		case TIMES: 
			return left.toString() + "*" +right.toString();
		case DIVIDE: 
			return left.toString() + "/" +right.toString();
		case MODULE:
			return left.toString() + "%" +right.toString();
		default:
			return "";
		}
	}
}

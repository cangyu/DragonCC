package compiler.semantic;

import compiler.ast.BinaryExpr.Operator;

public class Pointer extends Type
{
	Type elem_type;

	public Pointer(Type _t)
	{
		elem_type = _t;
		size = 4;
	}

	@Override
	public boolean equals(Type rhs)
	{
		if (this == rhs)
			return true;
		else if (rhs instanceof Pointer)
			return ((Pointer) this).elem_type.equals(((Pointer) rhs).elem_type);
		else
			return false;
	}

	@Override
	public boolean isAssignableWith(Type rhs)
	{
		return rhs instanceof Array || rhs instanceof Pointer;
	}

	@Override
	public boolean canOperateWith(Operator _op, Type _t)
	{
		switch (_op)
		{
		case PLUS:
			return (_t instanceof Int);
		case MINUS:
			return (_t instanceof Pointer) || (_t instanceof Int);
		case EQ:
		case NE:
		case LE:
		case LT:
		case GE:
		case GT:
			return (_t instanceof Array) || (_t instanceof Pointer);
		default:
			return false;
		}
	}

	@Override
	public boolean canBeCastTo(Type rhs)
	{
		return (rhs instanceof Char || rhs instanceof Int || rhs instanceof Pointer);
	}
}

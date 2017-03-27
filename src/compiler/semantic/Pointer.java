package compiler.semantic;

import compiler.ast.BinaryExpr;

public class Pointer extends Type
{
	Type elem_type;

	public Pointer(Type _t)
	{
		elem_type = _t;
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
		if (rhs instanceof Array || rhs instanceof Pointer)
			return true;
		else
			return false;
	}

	@Override
	public boolean canOperateWith(BinaryExpr.Type _op, Type _t)
	{
		if (_op == BinaryExpr.Type.PLUS)
		{
			if (_t instanceof Int)
				return true;
			else
				return false;
		}
		else if (_op == BinaryExpr.Type.MINUS)
		{
			if (_t instanceof Pointer)
				return true;
			else if (_t instanceof Int)
				return true;
			else
				return false;
		}
		else if (
			_op == BinaryExpr.Type.EQ || _op == BinaryExpr.Type.NE || _op == BinaryExpr.Type.LE || _op == BinaryExpr.Type.LT || _op == BinaryExpr.Type.GE
					|| _op == BinaryExpr.Type.GT
		)
		{
			if (_t instanceof Array || _t instanceof Pointer)
				return true;
			else
				return false;
		}
		else
			return false;
	}
}

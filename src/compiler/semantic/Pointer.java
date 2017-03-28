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
		if(this == rhs)
			return true;
		else if(rhs instanceof Pointer)
			return ((Pointer) this).elem_type.equals(((Pointer) rhs).elem_type);
		else
			return false;
	}

	@Override
	public boolean isAssignableWith(Type rhs)
	{
		if(rhs instanceof Array || rhs instanceof Pointer)
			return true;
		else
			return false;
	}

	@Override
	public boolean canOperateWith(Operator _op, Type _t)
	{
		if(_op == Operator.PLUS)
		{
			if(_t instanceof Int)
				return true;
			else
				return false;
		}
		else if(_op == Operator.MINUS)
		{
			if(_t instanceof Pointer)
				return true;
			else if(_t instanceof Int)
				return true;
			else
				return false;
		}
		else if(_op == Operator.EQ 
				|| _op == Operator.NE 
				|| _op == Operator.LE
				|| _op == Operator.LT 
				|| _op == Operator.GE 
				|| _op == Operator.GT)
		{
			if(_t instanceof Array || _t instanceof Pointer)
				return true;
			else
				return false;
		}
		else
			return false;
	}

	@Override
	public boolean canBeCastTo(Type rhs)
	{
		return (rhs instanceof Char || rhs instanceof Int || rhs instanceof Pointer);
	}
}

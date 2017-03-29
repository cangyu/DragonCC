package compiler.semantic;

import compiler.ast.BinaryExpr.Operator;

public final class Union extends Record
{
	public Union(String _name, Table _e)
	{
		super(_name, _e);
	}

	@Override
	public boolean equals(Type rhs)
	{
		return false;
	}

	@Override
	public boolean isAssignableWith(Type rhs)
	{
		if(rhs instanceof Union)
			return true;
		else
			return false;
	}

	@Override
	public boolean canOperateWith(Operator _op, Type _t)
	{
		return false;
	}

	@Override
	public boolean canBeCastTo(Type rhs)
	{
		return false;
	}

	@Override
	public int getSize()
	{
		// TODO Auto-generated method stub
		return 0;
	}
}

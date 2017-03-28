package compiler.semantic;

import compiler.ast.BinaryExpr.Operator;

public final class Function extends Type 
{
	Type arg_type;
	Type ret_type;
	
	public Function(Type arg_t, Type ret_t)
	{
		arg_type = arg_t;
		ret_type = ret_t;
	}

	@Override
	public boolean equals(Type rhs)
	{
		return false;
	}

	@Override
	public boolean isAssignableWith(Type rhs)
	{
		return false;
	}

	@Override
	public boolean canOperateWith(Operator op, Type t)
	{
		return false;
	}

	@Override
	public boolean canBeCastTo(Type rhs)
	{
		return false;
	}
}

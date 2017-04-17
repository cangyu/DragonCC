package compiler.semantic;

import compiler.ast.BinaryExpr.Operator;
import java.util.*;

public final class Function extends Type 
{
	LinkedList<Type> args;
	Type ret_type;
	
	public Function(Type ret_t)
	{
		args = new LinkedList<Type>();
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

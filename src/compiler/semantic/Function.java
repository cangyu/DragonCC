package compiler.semantic;

import java.util.LinkedList;

import compiler.ast.BinaryExpr.Operator;

public final class Function extends Type 
{
	String name;
	LinkedList<Type> args;
	Type ret_type;
	
	public Function(String _name, LinkedList<Type> _args, Type _ret)
	{
		name = _name;
		args = _args;
		ret_type = _ret;
	}

	@Override
	public boolean equals(Type rhs)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAssignableWith(Type rhs)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canOperateWith(Operator op, Type t)
	{
		// TODO Auto-generated method stub
		return false;
	}
}

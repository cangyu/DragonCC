package compiler.semantic;

import compiler.ast.BinaryExpr.Operator;
import java.util.LinkedList;

public class ArrayInitializer extends Type
{
	public LinkedList<Type> comp;
	
	public ArrayInitializer()
	{
		comp = new LinkedList<Type>();
	}
	
	public void add(Type x)
	{
		comp.add(x);
	}
	
	@Override
	public boolean equals(Type rhs)
	{
		return (rhs instanceof ArrayInitializer);
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

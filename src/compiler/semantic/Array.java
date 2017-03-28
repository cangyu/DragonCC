package compiler.semantic;

import java.util.Iterator;

import compiler.ast.BinaryExpr;

//not inherit from pointer, as pointer can 
//change where it points to, but array can not
public final class Array extends Type
{
	public int capacity;
	public Type elem_type;

	public Array(int _cap, Type _ct)
	{
		capacity = _cap;
		elem_type = _ct;
	}

	@Override
	public boolean equals(Type rhs)
	{
		if(this == rhs)
			return true;
		else if(rhs instanceof Array)
			return this.elem_type.equals(((Array) rhs).elem_type);
		else
			return false;
	}

	@Override
	public boolean isAssignableWith(Type rhs)
	{
		if(rhs instanceof ArrayInitializer)
		{
			ArrayInitializer ai = (ArrayInitializer) rhs;
			
			if(capacity >= ai.comp.size())
			{
				Iterator<Type> it = ai.comp.iterator();
				while(it.hasNext())
				{
					if(!elem_type.isAssignableWith(it.next()))
						return false;
				}
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}

	@Override
	public boolean canOperateWith(BinaryExpr.Operator _op, Type _t)
	{
		if(_op == BinaryExpr.Operator.PLUS)
		{
			if(_t instanceof Int)
				return true;
			else
				return false;
		}
		else if(_op == BinaryExpr.Operator.MINUS)
		{
			if(_t instanceof Array)
				return true;
			else if(_t instanceof Int)
				return true;
			else
				return false;
		}
		else if(_op == BinaryExpr.Operator.EQ || _op == BinaryExpr.Operator.NE || _op == BinaryExpr.Operator.LE
				|| _op == BinaryExpr.Operator.LT || _op == BinaryExpr.Operator.GE || _op == BinaryExpr.Operator.GT)
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
		return (rhs instanceof Pointer);
	}
}

package compiler.semantic;

import java.util.Iterator;
import compiler.ast.BinaryExpr.Operator;

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
	public boolean canOperateWith(Operator op, Type t)
	{
		switch(op)
		{
		case PLUS:
			return (t instanceof Int || t instanceof Char);
		case MINUS:
			return (t instanceof Int || t instanceof Char || t instanceof Array || t instanceof Pointer);
		case EQ:
		case NE:
		case LE:
		case LT:
		case GE:
		case GT:
			return (t instanceof Array || t instanceof Pointer);
		default:
			return false;
		}
	}

	@Override
	public boolean canBeCastTo(Type rhs)
	{
		return (rhs instanceof Pointer);
	}
}

package compiler.semantic;

import compiler.ast.BinaryExpr.Operator;
import compiler.ast.TypeSpecifier;

public final class Name extends Type 
{
	boolean isStruct;
	String name;
	
	public Name(boolean _iss, String _name)
	{
		isStruct = _iss;
		name = _name;
	}
	
	@Override
    public boolean equals(Type rhs)
    {
        if(this == rhs)
            return true;
        else if(rhs instanceof Name)
            return this.name.intern() == (((Name) rhs).name).intern();
        else
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

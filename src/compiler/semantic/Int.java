package compiler.semantic;

import compiler.ast.BinaryExpr;

public final class Int extends Type
{
    public static Int instance;
    
    public static Int getInstance()
    {
        if(instance == null)
        {
            instance = new Int();
            instance.size = 4;
        }
        return instance;
    }

    @Override
    public boolean equals(Type rhs)
    {
        if(this == rhs)
            return true;
        else if(rhs instanceof Int || rhs instanceof Char)
            return true;
        else
            return false;
    }

    @Override
    public boolean isAssignableWith(Type rhs)
    {
        return (rhs instanceof Int || rhs instanceof Char);
    }
    
    @Override
    public boolean canOperateWith(BinaryExpr.Operator op, Type rhs)
    {
        return (rhs instanceof Int || rhs instanceof Char);
    }

	@Override
	public boolean canBeCastTo(Type rhs)
	{
		return (rhs instanceof Char || rhs instanceof Int || rhs instanceof Pointer);
	}
}

package compiler.semantic;

import compiler.ast.BinaryExpr;

public final class Void extends Type
{
    private static Void instance;

    public static Void getInstance()
    {
        if(instance == null)
        {
        	instance = new Void();
        	instance.size = 1;
        }
        
        return instance;
    }

    @Override
    public boolean equals(Type rhs)
    {
    	return (rhs instanceof Void);
    }

    @Override
    public boolean isAssignableWith(Type rhs)
    {
        return false;
    }

    @Override
    public boolean canOperateWith(BinaryExpr.Operator _op, Type _t)
    {
        return false;
    }

	@Override
	public boolean canBeCastTo(Type rhs)
	{
		return false;
	}
}

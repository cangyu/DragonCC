package compiler.semantic;

import compiler.ast.BinaryExpr;

public final class Struct extends Record 
{
	public Struct(String _name, Table _e)
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
        if(rhs instanceof Struct)
            return true;
        else
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

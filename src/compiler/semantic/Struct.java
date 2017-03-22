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
	    if(this == rhs)
	        return true;
	    else if(rhs instanceof Struct)
	        return this.name.intern()==(((Struct)rhs).name).intern();
	    else
	        return false;
	}
	
	@Override
    public boolean isAssignable(Type rhs)
    {
        if(rhs instanceof Struct)
            return true;
        else
            return false;
    }
    
    @Override
    public boolean canOperateWith(BinaryExpr.Type _op, Type _t)
    {
        return false;
    }
}

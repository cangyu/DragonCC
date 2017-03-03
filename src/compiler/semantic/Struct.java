package compiler.semantic;

import java.util.LinkedList;

import compiler.ast.BinaryExpr;

public final class Struct extends Record 
{
	public Struct(String _name, LinkedList<RecordField> _field)
	{
		super(_name, _field);
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

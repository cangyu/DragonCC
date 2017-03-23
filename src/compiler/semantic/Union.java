package compiler.semantic;

import java.util.LinkedList;

import compiler.ast.BinaryExpr;

public final class Union extends Record
{
    public Union(String _name, Table _e)
    {
        super(_name, _e);
    }

    @Override
    public boolean equals(Type rhs)
    {
        if(this == rhs)
            return true;
        else if(rhs instanceof Union)
            return this.name.intern() == (((Union) rhs).name).intern();
        else
            return false;
    }
    
    @Override
    public boolean isAssignable(Type rhs)
    {
        if(rhs instanceof Union)
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

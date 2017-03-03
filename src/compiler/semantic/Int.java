package compiler.semantic;

import compiler.ast.BinaryExpr;

public final class Int extends Type
{
    public static Int instance;
    
    public static Int getInstance()
    {
        if(instance == null)
            instance = new Int();

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
    public boolean isAssignable(Type rhs)
    {
        if(rhs instanceof Int || rhs instanceof Char)
            return true;
        else
            return false;
    }
    
    @Override
    public boolean canOperateWith(BinaryExpr.Type _op, Type _t)
    {
        if(_t instanceof Int || _t instanceof Char)
            return true;
        else
            return false;
    }
}

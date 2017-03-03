package compiler.semantic;

import compiler.ast.BinaryExpr;

public final class Char extends Type
{
    public static Char instance;

    public static Char getInstance()
    {
        if(instance == null)
            instance = new Char();

        return instance;
    }

    @Override
    public boolean equals(Type rhs)
    {
        if(this == rhs)
            return true;
        else if(rhs instanceof Char || rhs instanceof Int)
            return true;
        else
            return false;
    }

    @Override
    public boolean isAssignable(Type rhs)
    {
        if(rhs instanceof Char || rhs instanceof Int)
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

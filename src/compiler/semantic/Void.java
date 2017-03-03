package compiler.semantic;

import compiler.ast.BinaryExpr;

public final class Void extends Type
{
    public static Void instance;

    public static Void getInstance()
    {
        if(instance == null)
            instance = new Void();

        return instance;
    }

    @Override
    public boolean equals(Type rhs)
    {
        if(this == rhs)
            return true;
        else
            return false;
    }

    @Override
    public boolean isAssignable(Type rhs)
    {
        return false;
    }

    @Override
    public boolean canOperateWith(BinaryExpr.Type _op, Type _t)
    {
        return false;
    }
}

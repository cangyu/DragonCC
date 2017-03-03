package compiler.semantic;

import compiler.ast.BinaryExpr;

public abstract class Type
{
    public boolean equals(Type rhs)
    {
        return false;
    }

    public boolean isAssignable(Type rhs)
    {
        return false;
    }

    public boolean canOperateWith(BinaryExpr.Type _op, Type _t)
    {
        return false;
    }
}

package compiler.semantic;

import compiler.ast.BinaryExpr;

public abstract class Type
{
    public abstract boolean equals(Type rhs);

    public abstract boolean isAssignableWith(Type rhs);

    public abstract boolean canOperateWith(BinaryExpr.Operator op, Type t);
}

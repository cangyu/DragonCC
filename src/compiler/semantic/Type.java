package compiler.semantic;

import compiler.ast.BinaryExpr;

public abstract class Type
{
	public int size;
	public boolean complete;
	
    public abstract boolean equals(Type rhs);

    public abstract boolean isAssignableWith(Type rhs);

    public abstract boolean canOperateWith(BinaryExpr.Operator op, Type t);
    
    public abstract boolean canBeCastTo(Type rhs);
}

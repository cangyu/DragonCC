package compiler.semantic;

import compiler.ast.BinaryExpr;

public final class Char extends Type
{
    public static Char instance;

    public static Char getInstance()
    {
        if(instance == null)
        {
            instance = new Char();
            instance.size = 1;
        }
        return instance;
    }

    @Override
    public boolean equals(Type rhs)
    {
        return (rhs instanceof Char || rhs instanceof Int);
    }

    @Override
    public boolean isAssignableWith(Type rhs)
    {
        return (rhs instanceof Char || rhs instanceof Int);
    }

    @Override
    public boolean canOperateWith(BinaryExpr.Operator op, Type rhs)
    {
        return (rhs instanceof Int || rhs instanceof Char);
    }

	@Override
	public boolean canBeCastTo(Type rhs)
	{
		return (rhs instanceof Char || rhs instanceof Int || rhs instanceof Pointer);
	}
}

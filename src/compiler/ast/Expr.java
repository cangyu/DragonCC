package compiler.ast;

import compiler.semantic.Type;

public abstract class Expr
{
    //general attributes for an expr
    public Type type;
    public boolean isLValue;//left value can be taken address of, while right value can not
    public boolean isConst;
    public Object val;
}


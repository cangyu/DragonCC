package compiler.semantic;

import compiler.ast.BinaryExpr;

//not inherit from pointer, as pointer can 
//change where it points to, but array can not
public final class Array extends Type
{
    public int capacity;
    public Type elem_type;

    public Array(int _cap, Type _ct)
    {
        capacity = _cap;
        elem_type = _ct;
    }
    
    @Override
    public boolean equals(Type rhs)
    {
        if(this == rhs)
            return true;
        else if (rhs instanceof Array)
            return this.elem_type.equals(((Array)rhs).elem_type);
        else
            return false;
    }
    
    @Override
    public boolean isAssignable(Type rhs)
    {
        if(rhs instanceof Array)
            return true;
        else
            return false;
    }
    
    @Override
    public boolean canOperateWith(BinaryExpr.Type _op, Type _t)
    {
        if(_op == BinaryExpr.Type.PLUS)
        {
            if(_t instanceof Int)
                return true;
            else
                return false;
        }
        else if(_op == BinaryExpr.Type.MINUS)
        {
            if(_t instanceof Array)
                return true;
            else if(_t instanceof Int)
                return true;
            else
                return false;
        }
        else if(_op == BinaryExpr.Type.EQ || _op == BinaryExpr.Type.NE
                || _op == BinaryExpr.Type.LE || _op == BinaryExpr.Type.LT
                || _op == BinaryExpr.Type.GE || _op == BinaryExpr.Type.GT)
        {
            if(_t instanceof Array || _t instanceof Pointer)
                return true;
            else
                return false;
        }
        else
            return false;
    }
}

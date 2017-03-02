package compiler.semantic;

//not inherit from pointer, as pointer can 
//change where it points to, but array can not
public final class Array extends Type
{
    public int capacity;
    public Type type;

    public Array(int _cap, Type _ct)
    {
        capacity = _cap;
        type = _ct;
    }
}

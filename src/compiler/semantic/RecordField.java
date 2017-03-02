package compiler.semantic;

public final class RecordField
{
    public Type type;
    public String name;
    
    public RecordField(Type _t, String _n)
    {
        type = _t;
        name = _n;
    }   
}

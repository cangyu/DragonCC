package compiler.semantic;

public class Entry 
{
	public Type type;
	public boolean isLValue;
	
	public Entry(Type _t, boolean _lval)
	{
		type = _t;
		isLValue = _lval;
	}
}

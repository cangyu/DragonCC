package compiler.semantic;

public class Entry 
{
	public Type type;
	public boolean isLvalue;
	
	public Entry(Type _t, boolean _lval)
	{
		type = _t;
		isLvalue = _lval;
	}
}

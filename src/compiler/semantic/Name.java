package compiler.semantic;

public final class Name extends Type 
{
	String name;
	Table env;
	
	public Name(String _name, Table _env)
	{
		name = _name;
		env = _env;
	}
}

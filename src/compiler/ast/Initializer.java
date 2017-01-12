package compiler.ast;

public class Initializer 
{
	public Expr value;
	public Initializers initializers;
	
	public Initializer(Expr _e)
	{
		value=_e;
	}
	
	public Initializer(Initializers _is)
	{
		initializers=_is;
	}
}

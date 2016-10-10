package compiler.ast;

public class Initializer 
{
	public Expr e;
	public Initializers is;
	
	public Initializer(Expr _e)
	{
		e=_e;
	}
	
	public Initializer(Initializers _is)
	{
		is=_is;
	}
}

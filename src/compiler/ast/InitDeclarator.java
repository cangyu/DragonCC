package compiler.ast;

public class InitDeclarator 
{
	public Declarator declr;
	public Initializer initr;
	
	public InitDeclarator(Declarator x, Initializer y)
	{
		declr=x;
		initr=y;
	}
}

package compiler.ast;

public class InitDeclarator 
{
	public Declarator declarator;
	public Initializer initializer;
	
	public InitDeclarator(Declarator x, Initializer y)
	{
		declarator=x;
		initializer=y;
	}
	
	public InitDeclarator(Declarator x)
	{
		declarator=x;
	}
}

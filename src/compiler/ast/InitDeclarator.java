package compiler.ast;

public class InitDeclarator 
{
	public Declarator declarator;
	public Initializer initializer;
	
	public InitDeclarator(Declarator _d, Initializer _i)
	{
		declarator = _d;
		initializer = _i;
	}
}

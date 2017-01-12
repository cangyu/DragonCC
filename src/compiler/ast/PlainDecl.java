package compiler.ast;

public class PlainDecl 
{
	public TypeSpecifier type;
	public Declarator declarator;
	
	public PlainDecl(TypeSpecifier t, Declarator d)
	{
		type=t;
		declarator=d;
	}
}

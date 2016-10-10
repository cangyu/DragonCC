package compiler.ast;

public class PlainDeclaration 
{
	public TypeSpecifier t;
	public Declarator declarator;
	
	public PlainDeclaration(TypeSpecifier _t, Declarator _d)
	{
		t=_t;
		declarator=_d;
	}
}

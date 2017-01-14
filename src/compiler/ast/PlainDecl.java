package compiler.ast;

public class PlainDecl
{
	public TypeSpecifier type_specifier;
	public Declarator declarator;
	
	public PlainDecl(TypeSpecifier _ts, Declarator _d)
	{
		type_specifier = _ts;
		declarator = _d;
	}
}

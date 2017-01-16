package compiler.ast;

public class NonInitDecl
{
	public TypeSpecifier type_specifier;
	public Declarators declarators;
	
	public NonInitDecl(TypeSpecifier _t, Declarators _decls)
	{
		type_specifier = _t;
		declarators = _decls;
	}
}

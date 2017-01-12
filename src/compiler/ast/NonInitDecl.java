package compiler.ast;

public class NonInitDecl
{
	public TypeSpecifier type;
	public Declarators declarators;
	
	public NonInitDecl(TypeSpecifier _t, Declarators _decls)
	{
		type=_t;
		declarators=_decls;
	}
}

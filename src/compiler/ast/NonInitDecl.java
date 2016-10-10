package compiler.ast;

public class NonInitDecl
{
	public TypeSpecifier t;
	public Declarators decls;
	
	public NonInitDecl(TypeSpecifier _t, Declarators _decls)
	{
		t=_t;
		decls=_decls;
	}
}

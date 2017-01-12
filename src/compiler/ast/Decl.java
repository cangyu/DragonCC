package compiler.ast;

public class Decl extends DeclOrFuncDef
{
	public TypeSpecifier type;
	public InitDeclarators init_declarators;
	
	public Decl(TypeSpecifier _t, InitDeclarators _ids)
	{
		type=_t;
		init_declarators=_ids;
	}
	
	public Decl(TypeSpecifier _t)
	{
		type=_t;
	}
}

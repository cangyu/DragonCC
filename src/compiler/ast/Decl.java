package compiler.ast;

public class Decl extends DeclOrFuncDef
{
	public TypeSpecifier type_specifier;
	public InitDeclarators init_declarators;
	
	public Decl(TypeSpecifier _ts, InitDeclarators _ids)
	{
		type_specifier = _ts;
		init_declarators = _ids;
	}
}

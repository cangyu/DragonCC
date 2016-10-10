package compiler.ast;

public class Decl extends DeclOrFuncDef 
{
	public TypeSpecifier t;
	public InitDeclarators ids;
	
	public Decl(TypeSpecifier _t, InitDeclarators _ids)
	{
		t=_t;
		ids=_ids;
	}
}

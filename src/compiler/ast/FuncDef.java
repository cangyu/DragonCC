package compiler.ast;

public class FuncDef extends DeclOrFuncDef
{
	public TypeSpecifier t;
	public PlainDeclarator name;
	public Parameters paras;
	public CompoundStmt compstmt;
	
	public FuncDef(TypeSpecifier _t, PlainDeclarator _name, Parameters _p, CompoundStmt _cs)
	{
		t=_t;
		name=_name;
		paras=_p;
		compstmt=_cs;
	}
}

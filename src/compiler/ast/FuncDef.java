package compiler.ast;

public class FuncDef extends DeclOrFuncDef
{
	public TypeSpecifier type;
	public PlainDeclarator id;
	public Parameters paras;
	public CompoundStmt comp_stmt;
	
	public FuncDef(TypeSpecifier _t, PlainDeclarator _id, Parameters _p, CompoundStmt _cs)
	{
		type=_t;
		id=_id;
		paras=_p;
		comp_stmt=_cs;
	}
}

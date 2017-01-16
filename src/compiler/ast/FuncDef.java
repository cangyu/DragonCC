package compiler.ast;

public class FuncDef extends GeneralDeclaration
{
	public TypeSpecifier type_specifier;
	public PlainDeclarator id;
	public Parameters paras;
	public CompoundStmt comp_stmt;
	
	public FuncDef(TypeSpecifier _ts, PlainDeclarator _id, Parameters _p, CompoundStmt _cs)
	{
		type_specifier = _ts;
		id = _id;
		paras = _p;
		comp_stmt = _cs;
	}
}

package compiler.ast;

public class FuncDef extends GeneralDeclaration
{
	public TypeSpecifier type_specifier;
	public PlainDeclarator func_name;
	public Parameters paras;
	public CompoundStmt comp_stmt;
	
	public FuncDef(TypeSpecifier _ts, PlainDeclarator _fn, Parameters _p, CompoundStmt _cs)
	{
		type_specifier = _ts;
		func_name = _fn;
		paras = _p;
		comp_stmt = _cs;
	}
}

package compiler.ast;

public class FuncDef extends ProgramComp
{
	public TypeSpecifier type_specifier;
	public PlainDeclarator func_name;
	public ParameterList params;
	public CompoundStmt comp_stmt;
	
	public FuncDef(TypeSpecifier _ts, PlainDeclarator _fn, ParameterList _p, CompoundStmt _cs)
	{
		type_specifier = _ts;
		func_name = _fn;
		params = _p;
		comp_stmt = _cs;
	}
	
	public void accept(ASTNodeVisitor v) throws Exception
	{
	    v.visit(this);
	}
}

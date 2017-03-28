package compiler.ast;

public class Declaration extends ProgramComp
{
	public TypeSpecifier type_specifier;
	public InitDeclaratorList init_declarator_list;
	
	public Declaration(TypeSpecifier _ts, InitDeclaratorList _ids)
	{
		type_specifier = _ts;
		init_declarator_list = _ids;
	}
	
	public void accept(ASTNodeVisitor v) throws Exception
    {
        v.visit(this);
    }
}

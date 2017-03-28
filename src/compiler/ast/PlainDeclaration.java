package compiler.ast;

public class PlainDeclaration extends ASTNode
{
	public TypeSpecifier type_specifier;
	public Declarator declarator;
	
	public PlainDeclaration(TypeSpecifier _ts, Declarator _d)
	{
		type_specifier = _ts;
		declarator = _d;
	}
	
    public void accept(ASTNodeVisitor v) throws Exception
    {
        v.visit(this);
    }
}

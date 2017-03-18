package compiler.ast;

public class TypeName extends ASTNode
{
	public TypeSpecifier type_specifier;
	public StarList star_list;
	
	public TypeName(TypeSpecifier _ts, StarList _ss)
	{
		type_specifier = _ts;
		star_list = _ss;
	}
	
    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

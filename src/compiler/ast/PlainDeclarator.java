package compiler.ast;

public class PlainDeclarator extends ASTNode
{
	public StarList star_list;
	public String identifier;
	
	public PlainDeclarator(StarList _s, String _id)
	{
	    star_list = _s;
	    identifier = _id;
	}
	
    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

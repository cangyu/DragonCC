package compiler.ast;

public class StarList extends ASTNode
{
	public int cnt;
	
	public StarList(int _ic)
	{
		cnt = _ic;
	}
	
    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

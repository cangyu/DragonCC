package compiler.ast;

public class StmtList extends ASTNode
{
	public Stmt head;
	public StmtList next;
	
	public StmtList(Stmt _s, StmtList _n)
	{
	    head = _s;
	    next = _n;
	}
	
    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

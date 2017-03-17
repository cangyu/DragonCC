package compiler.ast;

public class StmtList extends ASTNode
{
	public Stmt head;
	public StmtList next;
	
	public StmtList(Stmt _s)
	{
	    head = _s;
	    next = null;
	}
	
    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

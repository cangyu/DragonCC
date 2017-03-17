package compiler.ast;

public class SelectionStmt extends Stmt
{
	public Expr cond;
	public Stmt if_clause, else_clause;
	
	public SelectionStmt(Expr _cond, Stmt _ic, Stmt _ec)
	{
		cond = _cond;
		if_clause = _ic;
		else_clause = _ec;
	}
	
    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

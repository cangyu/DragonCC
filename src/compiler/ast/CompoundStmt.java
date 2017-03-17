package compiler.ast;

public class CompoundStmt extends Stmt
{
	public DeclarationList decls;
	public StmtList stmts;
	
	public CompoundStmt(DeclarationList _ds, StmtList _ss)
	{
		decls = _ds;
		stmts = _ss;
	}
	
    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

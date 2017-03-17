package compiler.ast;

public class InitDeclaratorList extends ASTNode
{    
	public InitDeclarator head;
	public InitDeclaratorList next;
	
	public InitDeclaratorList(InitDeclarator _idecl)
	{
	    head = _idecl;
	    next = null;
	}
	
    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

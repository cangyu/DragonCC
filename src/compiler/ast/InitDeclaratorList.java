package compiler.ast;

public class InitDeclaratorList extends ASTNode
{    
	public InitDeclarator head;
	public InitDeclaratorList next;
	
	public InitDeclaratorList(InitDeclarator _idecl, InitDeclaratorList _n)
	{
	    head = _idecl;
	    next = _n;
	}
	
    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

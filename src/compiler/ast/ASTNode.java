package compiler.ast;

public abstract class ASTNode
{
	public String [] ast_rep;
	
    abstract void accept(ASTNodeVisitor v);
}

package compiler.ast;

import java.util.LinkedList;

public class Declarator extends ASTNode
{
    public PlainDeclarator plain_declarator;
    public LinkedList<Expr> dimension;
    
    public Declarator(PlainDeclarator _pd)
    {
        plain_declarator = _pd;
        dimension = new LinkedList<Expr>();
    }
    
    public void accept(ASTNodeVisitor v) throws Exception
    {
        v.visit(this);
    }
}

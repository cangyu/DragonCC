package compiler.ast;

import java.util.LinkedList;

public class VarDeclarator extends Declarator
{
    public PlainDeclarator plain_declarator;
    public LinkedList<Expr> dimension;
    
    public VarDeclarator(PlainDeclarator _pd)
    {
        plain_declarator = _pd;
        dimension = new LinkedList<Expr>();
    }
    
    
    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}

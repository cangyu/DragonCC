package compiler.ast;

import java.util.LinkedList;

public class Declarator
{
	public PlainDeclarator pd;
	public LinkedList<Expr> shape;
	
	public Declarator(PlainDeclarator _pd)
	{
		pd=_pd;
		shape=new LinkedList<Expr>();
	}
}

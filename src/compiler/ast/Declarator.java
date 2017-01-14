package compiler.ast;

import java.util.LinkedList;

public class Declarator
{
	public String CN = getClass().getName();
	
	public PlainDeclarator plain_declarator;
	public LinkedList<Expr> dimension;
	
	public Declarator(PlainDeclarator _pd)
	{
		plain_declarator = _pd;
		dimension = new LinkedList<Expr>();
	}
}

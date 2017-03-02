package compiler.ast;

import java.util.LinkedList;

public class Declarator
{
	public PlainDeclarator plain_declarator;
	public boolean isFunc;
	public LinkedList<Expr> dimension;
	public Parameters param;
	
	public Declarator(PlainDeclarator _pd, boolean _isFunc, Parameters _param)
	{
		plain_declarator = _pd;
		isFunc = _isFunc;
		
		if(isFunc)
			param = _param;
		else
			dimension = new LinkedList<Expr>();
	}
}

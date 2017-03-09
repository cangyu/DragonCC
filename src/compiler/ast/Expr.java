package compiler.ast;

import compiler.semantic.Entry;

public abstract class Expr
{
    public Entry entry;
	public boolean isConst;
	public int val;
	
	@Override
	public String toString()
	{
	    return "Expr";
	}
}


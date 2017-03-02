package compiler.ast;

public abstract class Expr
{
	public boolean isConst;
	public int val;
	
	@Override
	public String toString()
	{
	    return "Expr";
	}
}


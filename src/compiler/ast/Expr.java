package compiler.ast;

public abstract class Expr
{
	public String CN = getClass().getName();
	
	public boolean is_const;
	public int value;
}


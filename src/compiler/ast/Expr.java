package compiler.ast;

import compiler.semantic.Type;

public abstract class Expr extends ASTNode
{
	public boolean isConst;
	public Object value;
	public boolean hasInitialized = false;
	public Type type;
	public boolean isLvalue = false;
}


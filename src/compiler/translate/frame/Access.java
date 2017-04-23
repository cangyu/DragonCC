package compiler.translate.frame;

import compiler.translate.ir.Exp;

public abstract class Access
{
	public abstract Exp access(Exp fp);
}

package compiler.translate;

import compiler.ast.BinaryExpr.Operator;

public class InMem extends Access
{
	int offset;
	
	@Override
	public Exp access(Exp fp)
	{
		Exp off = new Const(offset);
		Exp addr = new Binary(Operator.PLUS, fp, off);
		return new Mem(addr);
	}

}

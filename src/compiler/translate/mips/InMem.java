package compiler.translate.mips;

import compiler.ast.BinaryExpr.Operator;
import compiler.translate.frame.Access;
import compiler.translate.ir.Binary;
import compiler.translate.ir.Const;
import compiler.translate.ir.Exp;
import compiler.translate.ir.Mem;

public class InMem extends Access
{
	public int offset;
	
	public InMem(int n)
	{
		offset = n;
	}
	
	@Override
	public Exp access(Exp fp)
	{
		Exp off = new Const(offset);
		Exp addr = new Binary(Operator.PLUS, fp, off);
		return new Mem(addr);
	}

}

package compiler.translate.mips;

import compiler.translate.frame.Access;
import compiler.translate.ir.Exp;
import compiler.translate.temp.Temp;

public class InReg extends Access
{
	public Temp reg;
	
	public InReg()
	{
		reg = new Temp();
	}
	
	@Override
	public Exp access(Exp fp)
	{
		return reg;
	}

}

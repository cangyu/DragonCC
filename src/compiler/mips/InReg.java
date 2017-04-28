package compiler.mips;

import compiler.translate.Access;
import compiler.translate.Temp;

public class InReg extends Access
{
	public Temp reg;
	
	public InReg()
	{
		reg = new Temp();
	}
}

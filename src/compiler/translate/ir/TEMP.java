package compiler.translate.ir;

import compiler.translate.temp.Temp;

public class TEMP extends Exp
{
	Temp temp;
	
	public TEMP(Temp t)
	{
		temp = t;
	}
}

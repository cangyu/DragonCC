package compiler.translate.ir;

import compiler.translate.temp.Temp;

public class Move extends Stm
{
	public Exp src, dst;
	
	public Move(Temp _d, Exp _s)
	{
		dst = _d;
		src = _s;
	}
}

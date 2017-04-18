package compiler.translate;

public class Move extends Stm
{
	public Temp t;
	public Exp e;
	
	public Move(Temp _t, Exp _e)
	{
		t = _t;
		e = _e;
	}
}

package compiler.translate.ir;

public class Const extends Exp
{
	public int val;
	
	public Const(int _val)
	{
		val = _val;
	}
}

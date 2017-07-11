package compiler.IR;

public class Reg extends Operand
{
	public static int cnt = 0;

	public int index;

	public Reg()
	{
		index = ++cnt;
	}
}

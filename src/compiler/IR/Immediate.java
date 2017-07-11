package compiler.IR;

public class Immediate extends Operand
{
	public int num;

	public Immediate(int n)
	{
		num = n;
	}

	public Immediate()
	{
		num = 0;
	}
}

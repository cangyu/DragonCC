package compiler.IR;

public class Label extends Operand
{
	public static int cnt = 0;
	public String name;

	public Label(String x)
	{
		name = x;
		++cnt;
	}

	public Label()
	{
		name = "l" + cnt;
		++cnt;
	}
}

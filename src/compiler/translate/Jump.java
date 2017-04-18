package compiler.translate;

public class Jump extends Stm
{
	public Label labs;

	public Jump(Label _labs)
	{
		labs = _labs;
	}
}

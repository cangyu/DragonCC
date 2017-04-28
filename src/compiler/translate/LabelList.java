package compiler.translate;

public class LabelList
{
	public Label head;
	public LabelList next;
	
	public LabelList(Label _h, LabelList _n)
	{
		head = _h;
		next = _n;
	}
}

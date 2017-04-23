package compiler.translate.ir;

import compiler.translate.temp.LabelList;

public class Jump extends Stm
{
	public Exp e;
	public LabelList label_list;

	public Jump(Exp _e, LabelList _labs)
	{
		e = _e;
		label_list = _labs;
	}
}

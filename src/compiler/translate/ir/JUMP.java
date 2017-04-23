package compiler.translate.ir;

import compiler.translate.temp.LabelList;

public class JUMP extends Stm
{
	public Exp e;
	public LabelList label_list;

	public JUMP(Exp _e, LabelList _labs)
	{
		e = _e;
		label_list = _labs;
	}
}

package compiler.translate.ir;

import compiler.translate.temp.Label;

public class LABEL extends Stm
{
	public Label label;
	
	public LABEL(Label l)
	{
		label = l;
	}
}

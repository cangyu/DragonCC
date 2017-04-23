package compiler.translate.ir;

import compiler.translate.temp.Label;

public class CJUMP extends Stm
{
	public int op;
	public Exp left, right;
	public Label true_stm, false_stm;

	public CJUMP(int _op, Exp _el, Exp _er, Label _ts, Label _fs)
	{
		op = _op;
		left = _el;
		right = _er;
		true_stm = _ts;
		false_stm = _fs;
	}
}

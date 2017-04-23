package compiler.translate;

import compiler.translate.ir.Stm;
import compiler.translate.temp.Label;

public class Ex extends Exp
{
	public compiler.translate.ir.Exp exp;
	
	public Ex(compiler.translate.ir.Exp e)
	{
		exp = e;
	}

	@Override
	public compiler.translate.ir.Exp unEx()
	{
		return exp;
	}

	@Override
	public Stm unNx()
	{
		return null;
	}

	@Override
	public Stm unCx(Label t, Label f)
	{
		return null;
	}

}

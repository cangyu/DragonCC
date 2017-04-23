package compiler.translate;

import compiler.translate.ir.Stm;
import compiler.translate.temp.Label;

public class Nx extends Exp
{
	public Stm stm;
	public Nx(Stm s)
	{
		stm = s;
	}

	@Override
	public compiler.translate.ir.Exp unEx()
	{
		return null;
	}

	@Override
	public Stm unNx()
	{
		return stm;
	}

	@Override
	public Stm unCx(Label t, Label f)
	{
		return null;
	}

}

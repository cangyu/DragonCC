package compiler.translate;

import compiler.translate.temp.Label;

public abstract class Exp
{
	public abstract compiler.translate.ir.Exp unEx();

	public abstract compiler.translate.ir.Stm unNx();

	public abstract compiler.translate.ir.Stm unCx(Label t, Label f);
}

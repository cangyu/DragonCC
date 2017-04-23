package compiler.translate.frame;

import compiler.translate.ir.Exp;
import compiler.translate.ir.ExpList;
import compiler.translate.ir.InstrList;
import compiler.translate.ir.Stm;
import compiler.translate.temp.Label;
import compiler.translate.temp.Temp;
import compiler.translate.temp.TempList;

public abstract class Frame
{
	public Label name;
	public AccessList formals = null;

	public abstract Frame newFrame(Label name, BoolList formals);
	public abstract Access allocLocal(boolean escape);
	public abstract Exp externalCall(String func, ExpList args);
	public abstract Temp FP();
	public abstract Temp SP();
	public abstract Temp RA();
	public abstract Temp RV();
	public abstract TempList regs();//List of registers
	public abstract Stm procEntryExit1(Stm body);
	public abstract InstrList procEntryExit2(InstrList body);
	public abstract InstrList procEntryExit3(InstrList body);
	public abstract String str(Label l, String val);
	public abstract InstrList codegen(Stm s);
	public abstract int wordSize();
}

package compiler.translate;

public abstract class Frame
{
	public Label name;
	public AccessList formals = null;

	public abstract Frame newFrame(Label name, boolean[] formals);
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

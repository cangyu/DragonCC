package compiler.translate.mips;

import compiler.translate.frame.Access;
import compiler.translate.frame.BoolList;
import compiler.translate.frame.Frame;
import compiler.translate.ir.Exp;
import compiler.translate.ir.ExpList;
import compiler.translate.ir.InstrList;
import compiler.translate.ir.Stm;
import compiler.translate.temp.Label;
import compiler.translate.temp.Temp;
import compiler.translate.temp.TempList;

public class MipsFrame extends Frame
{

	@Override
	public Frame newFrame(Label name, BoolList formals)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Access allocLocal(boolean escape)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp externalCall(String func, ExpList args)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Temp FP()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Temp SP()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Temp RA()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Temp RV()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TempList regs()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stm procEntryExit1(Stm body)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstrList procEntryExit2(InstrList body)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstrList procEntryExit3(InstrList body)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String str(Label l, String val)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstrList codegen(Stm s)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int wordSize()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}

package compiler.translate.mips;

import compiler.translate.frame.Access;
import compiler.translate.frame.AccessList;
import compiler.translate.frame.BoolList;
import compiler.translate.frame.Frame;
import compiler.translate.temp.Label;

public class MipsFrame extends Frame
{
	int cur_alloc_pos;
	static final int word_len = 4;
	
	public MipsFrame()
	{
		cur_alloc_pos = 0;
	}
	
	@Override
	public Frame newFrame(Label name, BoolList formals)
	{
		Frame ret = new MipsFrame();
		ret.name = name;

		BoolList f = formals;
		while (f != null)
		{
			Access a = ret.allocLocal(f.head);
			ret.formals = new AccessList(a, ret.formals);
			f = f.next;
		}

		return ret;
	}

	@Override
	public Access allocLocal(boolean escape)
	{
		Access ret = null;
		if(escape)
		{
			ret = new InMem(cur_alloc_pos);
			cur_alloc_pos -= word_len;
		}
		else
		{
			ret = new InReg();
		}
		
		return ret;
	}

}

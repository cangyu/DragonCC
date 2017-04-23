package compiler.translate.mips;

import compiler.translate.frame.Access;

public class InMem extends Access
{
	public int offset;
	
	public InMem(int n)
	{
		offset = n;
	}
}

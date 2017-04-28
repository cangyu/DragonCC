package compiler.mips;

import compiler.translate.Access;

public class InMem extends Access
{
	public int offset;
	
	public InMem(int n)
	{
		offset = n;
	}
}

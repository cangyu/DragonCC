package compiler.IR;

import java.util.LinkedList;

public class ILOCProgram
{
	public LinkedList<InstructionAbs> tac_list;

	public ILOCProgram()
	{
		tac_list = new LinkedList<InstructionAbs>();
	}
}

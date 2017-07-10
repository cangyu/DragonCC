package compiler.IR;

import java.util.LinkedList;

public class OperationBlock extends Instruction
{
	public LinkedList<Operation> op_list;
	
	public OperationBlock()
	{
		op_list = new LinkedList<Operation>();
	}
}

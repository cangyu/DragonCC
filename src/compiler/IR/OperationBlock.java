package compiler.IR;

import java.util.LinkedList;

public class OperationBlock extends Instruction
{
	public LinkedList<Operation> op_list;

	public OperationBlock(Operation... args)
	{
		op_list = new LinkedList<Operation>();
		for (Operation v : args)
			op_list.add(v);
	}
}

package compiler.IR;

import java.util.LinkedList;

public class OperandList
{
	public LinkedList<Operand> var_list;

	public OperandList(Operand... args)
	{
		var_list = new LinkedList<Operand>();
		for (Operand v : args)
			var_list.add(v);
	}
}

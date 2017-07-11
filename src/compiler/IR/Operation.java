package compiler.IR;

public class Operation extends Instruction
{
	public Opcode op;
	public OperandList src_list;
	public OperandList dst_list;

	public Operation(Opcode x, OperandList y1, OperandList y2)
	{
		op = x;
		src_list = y1;
		dst_list = y2;
	}
}

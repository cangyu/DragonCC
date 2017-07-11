package compiler.IR;

public class CBR_GT extends ControlFlowOp
{
	public CBR_GT(Immediate cc1, Label l2, Label l3)
	{
		super(Opcode.cbr_GT, new OperandList(cc1), new OperandList(l2, l3));
	}
}

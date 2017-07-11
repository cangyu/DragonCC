package compiler.IR;

public class CBR_LE extends ControlFlowOp
{
	public CBR_LE(Immediate cc1, Label l2, Label l3)
	{
		super(Opcode.cbr_LE, new OperandList(cc1), new OperandList(l2, l3));
	}
}

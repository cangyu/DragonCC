package compiler.IR;

public class CBR_LT extends ControlFlowOp
{
	public CBR_LT(Immediate cc1, Label l2, Label l3)
	{
		super(Opcode.cbr_LT, new OperandList(cc1), new OperandList(l2, l3));
	}
}

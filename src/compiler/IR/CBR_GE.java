package compiler.IR;

public class CBR_GE extends ControlFlowOp
{
	public CBR_GE(Immediate cc1, Label l2, Label l3)
	{
		super(Opcode.cbr_GE, new OperandList(cc1), new OperandList(l2, l3));
	}
}

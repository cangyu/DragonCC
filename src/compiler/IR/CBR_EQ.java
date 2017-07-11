package compiler.IR;

public class CBR_EQ extends ControlFlowOp
{
	public CBR_EQ(Immediate cc1, Label l2, Label l3)
	{
		super(Opcode.cbr_EQ, new OperandList(cc1), new OperandList(l2, l3));
	}
}

package compiler.IR;

public class CBR extends ControlFlowOp
{
	public CBR(Reg r1, Label l2, Label l3)
	{
		super(Opcode.cbr, new OperandList(r1), new OperandList(l2, l3));
	}
}

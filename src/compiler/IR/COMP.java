package compiler.IR;

public class COMP extends ControlFlowOp
{
	public COMP(Reg r1, Reg r2, Immediate cc3)
	{
		super(Opcode.comp, new OperandList(r1, r2), new OperandList(cc3));
	}
}

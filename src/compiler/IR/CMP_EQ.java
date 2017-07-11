package compiler.IR;

public class CMP_EQ extends ControlFlowOp
{
	public CMP_EQ(Reg r1, Reg r2, Reg r3)
	{
		super(Opcode.cmp_EQ, new OperandList(r1, r2), new OperandList(r3));
	}
}

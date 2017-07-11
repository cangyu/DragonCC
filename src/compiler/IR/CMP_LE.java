package compiler.IR;

public class CMP_LE extends ControlFlowOp
{
	public CMP_LE(Reg r1, Reg r2, Reg r3)
	{
		super(Opcode.cmp_LE, new OperandList(r1, r2), new OperandList(r3));
	}
}

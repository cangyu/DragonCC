package compiler.IR;

public class CMP_LT extends ControlFlowOp
{
	public CMP_LT(Reg r1, Reg r2, Reg r3)
	{
		super(Opcode.cmp_LT, new OperandList(r1, r2), new OperandList(r3));
	}
}

package compiler.IR;

public class CMP_NE extends ControlFlowOp
{
	public CMP_NE(Reg r1, Reg r2, Reg r3)
	{
		super(Opcode.cmp_NE, new OperandList(r1, r2), new OperandList(r3));
	}
}

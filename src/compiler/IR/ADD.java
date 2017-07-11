package compiler.IR;

public class ADD extends NormalOp
{
	public ADD(Reg r1, Reg r2, Reg r3)
	{
		super(Opcode.add, new OperandList(r1, r2), new OperandList(r3));
	}
}

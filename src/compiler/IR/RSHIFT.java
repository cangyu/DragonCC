package compiler.IR;

public class RSHIFT extends NormalOp
{
	public RSHIFT(Reg r1, Reg r2, Reg r3)
	{
		super(Opcode.rshift, new OperandList(r1, r2), new OperandList(r3));
	}
}

package compiler.IR;

public class LSHIFT extends NormalOp
{
	public LSHIFT(Reg r1, Reg r2, Reg r3)
	{
		super(Opcode.lshift, new OperandList(r1, r2), new OperandList(r3));
	}
}

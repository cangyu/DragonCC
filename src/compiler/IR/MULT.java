package compiler.IR;

public class MULT extends NormalOp
{
	public MULT(Reg r1, Reg r2, Reg r3)
	{
		super(Opcode.mult, new OperandList(r1, r2), new OperandList(r3));
	}
}

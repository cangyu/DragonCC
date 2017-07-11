package compiler.IR;

public class ANDI extends NormalOp
{
	public ANDI(Reg r1, Immediate c2, Reg r3)
	{
		super(Opcode.andI, new OperandList(r1, c2), new OperandList(r3));
	}
}

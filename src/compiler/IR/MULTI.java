package compiler.IR;

public class MULTI extends NormalOp
{
	public MULTI(Reg r1, Immediate c2, Reg r3)
	{
		super(Opcode.multI, new OperandList(r1, c2), new OperandList(r3));
	}
}

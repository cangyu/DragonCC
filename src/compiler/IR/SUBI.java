package compiler.IR;

public class SUBI extends NormalOp
{
	public SUBI(Reg r1, Immediate c2, Reg r3)
	{
		super(Opcode.subI, new OperandList(r1, c2), new OperandList(r3));
	}
}

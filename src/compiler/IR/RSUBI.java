package compiler.IR;

public class RSUBI extends NormalOp
{
	public RSUBI(Reg r1, Immediate c2, Reg r3)
	{
		super(Opcode.rsubI, new OperandList(r1, c2), new OperandList(r3));
	}
}

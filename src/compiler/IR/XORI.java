package compiler.IR;

public class XORI extends NormalOp
{
	public XORI(Reg r1, Immediate c2, Reg r3)
	{
		super(Opcode.xorI, new OperandList(r1, c2), new OperandList(r3));
	}
}

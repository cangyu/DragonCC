package compiler.IR;

public class CLOADAI extends NormalOp
{
	public CLOADAI(Reg r1, Immediate c2, Reg r3)
	{
		super(Opcode.cloadAI, new OperandList(r1, c2), new OperandList(r3));
	}
}

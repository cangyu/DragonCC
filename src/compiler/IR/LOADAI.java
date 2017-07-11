package compiler.IR;

public class LOADAI extends NormalOp
{
	public LOADAI(Reg r1, Immediate c2, Reg r3)
	{
		super(Opcode.loadAI, new OperandList(r1, c2), new OperandList(r3));
	}
}

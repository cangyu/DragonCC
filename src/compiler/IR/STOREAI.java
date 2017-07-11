package compiler.IR;

public class STOREAI extends NormalOp
{
	public STOREAI(Reg r1, Reg r2, Immediate c3)
	{
		super(Opcode.storeAI, new OperandList(r1), new OperandList(r2, c3));
	}
}

package compiler.IR;

public class CSTOREAI extends NormalOp
{
	public CSTOREAI(Reg r1, Reg r2, Immediate c3)
	{
		super(Opcode.cstoreAI, new OperandList(r1), new OperandList(r2, c3));
	}
}

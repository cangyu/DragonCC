package compiler.IR;

public class CSTOREAO extends NormalOp
{
	public CSTOREAO(Reg r1, Reg r2, Reg r3)
	{
		super(Opcode.cstoreAO, new OperandList(r1), new OperandList(r2, r3));
	}
}

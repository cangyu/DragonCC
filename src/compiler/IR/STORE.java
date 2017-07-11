package compiler.IR;

public class STORE extends NormalOp
{
	public STORE(Reg r1, Reg r2)
	{
		super(Opcode.store, new OperandList(r1), new OperandList(r2));
	}
}

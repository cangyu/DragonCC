package compiler.IR;

public class CLOAD extends NormalOp
{
	public CLOAD(Reg r1, Reg r2)
	{
		super(Opcode.cload, new OperandList(r1), new OperandList(r2));
	}
}

package compiler.IR;

public class C2I extends NormalOp
{
	public C2I(Reg r1, Reg r2)
	{
		super(Opcode.c2i, new OperandList(r1), new OperandList(r2));
	}
}

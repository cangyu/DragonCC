package compiler.IR;

public class C2C extends NormalOp
{
	public C2C(Reg r1, Reg r2)
	{
		super(Opcode.c2c, new OperandList(r1), new OperandList(r2));
	}
}

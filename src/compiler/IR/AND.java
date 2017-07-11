package compiler.IR;

public class AND extends NormalOp
{
	public AND(Reg r1, Reg r2, Reg r3)
	{
		super(Opcode.and, new OperandList(r1, r2), new OperandList(r3));
	}
}

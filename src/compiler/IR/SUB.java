package compiler.IR;

public class SUB extends NormalOp
{
	public SUB(Reg r1, Reg r2, Reg r3)
	{
		super(Opcode.sub, new OperandList(r1, r2), new OperandList(r3));
	}
}

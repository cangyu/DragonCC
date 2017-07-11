package compiler.IR;

public class LOAD extends NormalOp
{
	public LOAD(Reg r1, Reg r2)
	{
		super(Opcode.load, new OperandList(r1), new OperandList(r2));
	}
}

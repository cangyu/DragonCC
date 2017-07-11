package compiler.IR;

public class LOADAO extends NormalOp
{
	public LOADAO(Reg r1, Reg r2, Reg r3)
	{
		super(Opcode.loadAO, new OperandList(r1, r2), new OperandList(r3));
	}
}

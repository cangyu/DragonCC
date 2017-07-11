package compiler.IR;

public class DIVI extends NormalOp
{
	public DIVI(Reg r1, Immediate c2, Reg r3)
	{
		super(Opcode.divI, new OperandList(r1, c2), new OperandList(r3));
	}
}

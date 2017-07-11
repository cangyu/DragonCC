package compiler.IR;

public class RDIVI extends NormalOp
{
	public RDIVI(Reg r1, Immediate c2, Reg r3)
	{
		super(Opcode.rdivI, new OperandList(r1, c2), new OperandList(r3));
	}
}

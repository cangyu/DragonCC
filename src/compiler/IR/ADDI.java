package compiler.IR;

public class ADDI extends NormalOp
{
	public ADDI(Reg r1, Immediate c2, Reg r3)
	{
		super(Opcode.addI, new OperandList(r1, c2), new OperandList(r3));
	}
}

package compiler.IR;

public class PHI extends NormalOp
{
	public PHI(Reg rm, Reg...regs)
	{
		super(Opcode.phi, new OperandList(regs), new OperandList(rm));
	}
}

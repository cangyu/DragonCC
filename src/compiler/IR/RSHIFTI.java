package compiler.IR;

public class RSHIFTI extends NormalOp
{
	public RSHIFTI(Reg r1, Immediate c2, Reg r3)
	{
		super(Opcode.rshiftI, new OperandList(r1, c2), new OperandList(r3));
	}
}

package compiler.IR;

public class LSHIFTI extends NormalOp
{
	public LSHIFTI(Reg r1, Immediate c2, Reg r3)
	{
		super(Opcode.lshiftI, new OperandList(r1, c2), new OperandList(r3));
	}
}

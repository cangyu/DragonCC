package compiler.IR;

public class I2I extends NormalOp
{
	public I2I(Reg r1, Reg r2)
	{
		super(Opcode.i2i, new OperandList(r1), new OperandList(r2));
	}
}

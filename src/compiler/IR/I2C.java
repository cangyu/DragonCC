package compiler.IR;

public class I2C extends NormalOp
{
	public I2C(Reg r1, Reg r2)
	{
		super(Opcode.i2c, new OperandList(r1), new OperandList(r2));
	}
}

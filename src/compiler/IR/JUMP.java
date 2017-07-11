package compiler.IR;

public class JUMP extends ControlFlowOp
{
	public JUMP(Reg r1)
	{
		super(Opcode.jump, null, new OperandList(r1));
	}
}

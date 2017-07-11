package compiler.IR;

public class LabeledInstruction extends InstructionAbs
{
	public Label label;
	public Instruction instr;

	public LabeledInstruction(Label x, Instruction y)
	{
		label = x;
		instr = y;
	}
}

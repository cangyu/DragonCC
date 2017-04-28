package compiler.translate;

public class Label
{
    public String name;
    private static int cnt = 0;

    public Label()
    {
        name = "Label" + cnt++;
    }

    public Label(String s)
    {
        name = s;
    }

    @Override
    public String toString()
    {
        return name;
    }
}

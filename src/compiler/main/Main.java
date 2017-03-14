package compiler.main;

import java.io.*;
import java.util.*;
import org.json.*;
import com.google.gson.*;
import java_cup.runtime.*;
import compiler.syntactic.*;
import compiler.semantic.*;

public class Main 
{    
	public static void main(String[] args) throws Exception
	{
        InputStream inp = new FileInputStream("tests/example.c");
        Parser parser = new Parser(inp);
        java_cup.runtime.Symbol parseTree = null;

        try
        {
            parseTree = parser.parse();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            throw new Error(e.toString());
        }
        finally
        {
            inp.close();
        }

        Semantic sc = new Semantic(parseTree.value);
        sc.checkProgram();
	}
}

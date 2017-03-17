package compiler.main;

import java.io.*;
import java.util.*;
import org.json.*;
import com.google.gson.*;
import java_cup.runtime.*;
import compiler.syntactic.*;
import compiler.ast.*;
import compiler.semantic.*;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Compiling: " + args[0]);
        Compile(args[0]);
        System.out.println("Compilation Done!");
    }

    public static void Compile(String _filename) throws Exception
    {
        System.out.println("\tParsing...");
        Program prog = Parse(_filename);
        System.out.println("\tParsing Done!");
        
        System.out.println("\tBuilding AST...");
        BuildAST(prog);
        System.out.println("\tBuilding AST Done!");

        // Semantic sc = new Semantic(prog);
        // sc.checkProgram();
    }

    public static Program Parse(String _filename) throws Exception
    {
        InputStream inp = new FileInputStream(_filename);
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

        return (Program) parseTree.value;
    }

    public static void BuildAST(Program prog)
    {
        
    }

}

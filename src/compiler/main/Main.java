package compiler.main;

import java.io.*;
import compiler.syntactic.*;
import compiler.ast.*;
import compiler.semantic.*;
import compiler.translate.*;

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
		String src_path = "test/" + _filename;
		String ast_filename = "result/" + _filename + "_AST.txt";
		String prettier_filename = "result/" + _filename + "_PP.c";

		System.out.println("\tParsing...");
		Program prog = Parse(src_path);
		System.out.println("\tParsing Done!");
		
		System.out.println("\tBuilding AST...");
		BufferedWriter ast_out = new BufferedWriter(new FileWriter(ast_filename));
		ASTPrinter ast_printer = new ASTPrinter();
		prog.accept(ast_printer);
		for (String str : prog.ast_rep)
			ast_out.write(str+"\n");
		ast_out.close();
		System.out.println("\tOutput at: " + ast_filename);
		System.out.println("\tBuilding AST Done!");
		
		System.out.println("\tReformating Source Code...");
		BufferedWriter ppc_out = new BufferedWriter(new FileWriter(prettier_filename));
		PrettyPrinter pp = new PrettyPrinter();
		prog.accept(pp);
		for (String str : prog.code_rep)
			ppc_out.write(str+"\n");
		ppc_out.close();
		System.out.println("\tOutput at: " + prettier_filename);
		System.out.println("\tReformat Done!");
		
		System.out.println("\tSemantic Checking...");
		Semantic sc = new Semantic(prog);
		sc.check();
		System.out.println("\tSemantic Check Done!");
		
		System.out.println("\tTranslating...");
		Translator irt = new Translator(prog);
		irt.translate();
		System.out.println("\tTranslation Done!");
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
}

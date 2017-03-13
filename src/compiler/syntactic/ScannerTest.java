package compiler.syntactic;

import java_cup.runtime.*;
import java.io.*;

public final class ScannerTest implements Symbols {

	public static void main(String[] args) throws Exception {
		System.out.println("===ScannerTest : Self-Testing===");
		scan("tests/example.c");
		scan("tests/example1.c");
		scan("tests/example2.c");
		scan("tests/example3.c");
		System.out.println("===ScannerTest : Self-Testing Over===");
	}
	
	public static void scan(String filename) throws Exception {
		System.out.println("==Scanning : " + filename + "==");
		java.io.InputStream in=new java.io.FileInputStream(filename);
		java_cup.runtime.Scanner lexer = new Lexer(new InputStreamReader(in));
		java_cup.runtime.Symbol tok = null;
		
		do {
			tok = lexer.next_token();
			if (tok.sym == NUM) {
				System.out.println(tok.value);
			} else if (tok.sym == ID) {
				System.out.println(tok.value + " ");
			} else {
				System.out.println(symnames[tok.sym]);
			}
		} while (tok.sym != EOF);
		
		in.close();
	}
	
	static String[] symnames = new String[1000];
	static {
		symnames[TIMES] = "*";
		symnames[DIVIDE] = " / ";
		symnames[LPAREN] = "(";
		symnames[INT] = "int ";
		symnames[MINUS] = " - ";
		symnames[RPAREN] = ") ";
		symnames[SEMI] = ";\n";
		symnames[PLUS] = " + ";
		symnames[ASSIGN] = " = ";
		symnames[IF] = "if ";
		symnames[EOF] = "<EOF>";
		symnames[RETURN] = "return ";
		symnames[error] = "<error>";
		symnames[EQ] = " == ";
		symnames[LBRACE] = "{\n";
		symnames[RBRACE] = "}\n";
		symnames[LT] = " < ";
		symnames[GT] = " > ";
	}
}
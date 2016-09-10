all: Parser.java Symbols.java Lexer.java

Parser.java: Parser.cup
	java -jar ../../../lib/java-cup-11b.jar -parser Parser -symbols Symbols -interface < Parser.cup

Symbols.java: Parser.cup
	java -jar ../../../lib/java-cup-11b.jar -parser Parser -symbols Symbols -interface < Parser.cup

Lexer.java: Lexer.jflex
	java -cp ../../../lib/jflex-1.6.1.jar jflex.Main Lexer.jflex

clean:
	rm -f Lexer.java


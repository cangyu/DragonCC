JAVA_CUP = ../../../lib/java-cup-11b.jar
JFLEX = ../../../lib/jflex-1.6.1.jar

all: Parser.java Symbols.java Lexer.java

Parser.java: Parser.cup
	java -jar $(JAVA_CUP) -parser Parser -symbols Symbols -interface < Parser.cup

Symbols.java: Parser.cup
	java -jar $(JAVA_CUP) -parser Parser -symbols Symbols -interface < Parser.cup

Lexer.java: Lexer.flex
	java -cp $(JFLEX) jflex.Main Lexer.flex

clean:
	rm -f Lexer.java Parser.java Symbols.java


all:
	cd src/compiler/syntactic && make

clean:
	cd src/compiler/syntactic && make clean
	rm -rf bin/

package compiler.IR;

public enum Opcode
{
	add, sub, mult, div, addI, subI, rsubI, multI, divI, rdivI, lshift, lshiftI, rshift, rshiftI, and, andI, or, orI, xor, xorI, load, loadI, loadAI, loadAO, cload, cloadAI, cloadAO, store, storeAI, storeAO, cstore, cstoreAI, cstoreAO, i2i, c2c, c2i, i2c, jump, jumpI, cbr, tbl, cmp_LT, cmp_LE, cmp_EQ, cmp_NE, cmp_GT, cmp_GE, comp, cbr_LT, cbr_LE, cbr_EQ, cbr_NE, cbr_GT, cbr_GE, phi;
}

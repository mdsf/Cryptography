package test1;

import java.io.File;

public abstract class Encryption {
	
	abstract void encrypt(File inFilename,File outFilename,File keyFilename);
}

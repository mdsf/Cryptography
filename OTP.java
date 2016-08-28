package test1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class OTP extends Encryption {

	public void encrypt(File inFilename,File outFilename,File keyFilename)
	{
		int text,key = 0;
		try (DataInputStream in = 
				new DataInputStream(new BufferedInputStream(new FileInputStream(keyFilename)))){
			try {
				while ((key = in.read()) != -1)
					key = in.read();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try (DataInputStream in = 
				new DataInputStream(new BufferedInputStream(new FileInputStream(inFilename)) ); 
			DataOutputStream out = 
					new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outFilename))))	{
			while ((text = in.read()) != -1) {
				//Write the XOR to the output file
				int results = (byte)(text ^ key);
				out.write(results);
            }			
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
  }
}



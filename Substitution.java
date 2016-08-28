package test1;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Substitution extends Encryption {
	public static final int SMALLEST_PRINTABLE_ASCII = 32;
	public static final int LARGEST_PRINTABLE_ASCII = 126;
	
	public void encrypt(File inFilename,File outFilename,File keyFilename) 
	{
		int text;
		//key file
		ArrayList<Byte> cipher = ReadFromFile(keyFilename);
		byte[] result = getBytes(cipher);
		try (DataInputStream in = 
				new DataInputStream(new BufferedInputStream(new FileInputStream(keyFilename)))){
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
				//Write the mapped byte to the output file
				out.write(mappedByte(text,result));
            }			
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}
	//converts array list to bytes
	private byte[] getBytes(ArrayList<Byte> input)
	{
		byte[] b = new byte[input.size()];
		for(int i =0;i<b.length;i++)
		{
			b[i] = input.get(i);
		}
		return b;
	}
	
	public ArrayList<Byte> ReadFromFile(File inFilename) {
		// TODO Auto-generated method stub
		
		ArrayList<Byte> output = new ArrayList<Byte>();
		DataInputStream read =null;
		try {
			read = new DataInputStream(new FileInputStream(inFilename));

			try {
				
				while(true)
				{
					try
					{
					byte b = read.readByte();
					output.add(b);
					}
					catch(EOFException e)
					{
						break;
					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return output;
	}
	
	private int mappedByte(int c,byte[] key){
		if (c < SMALLEST_PRINTABLE_ASCII || c > LARGEST_PRINTABLE_ASCII)
			return c;
		else
		return key[c - SMALLEST_PRINTABLE_ASCII];
		
		}
	
	public void WriteToFile(byte[] decrypted,File outFilename){
		BufferedOutputStream out = null;	
		try {
		
			out =  new BufferedOutputStream(new FileOutputStream(outFilename));
			try {
				out.write(decrypted,0,decrypted.length);
			} 	catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{
		out.close();
			}
			catch(IOException e){
				
			}
		}
	}
}

package test1;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;

public class CryptographicUtilities {

	public static final int SMALLEST_PRINTABLE_ASCII = 32;
	public static final int PRINTABLE_ASCII = 95;
	public void substitutionKeys(File encryptkeyFilename,File decryptkeyFilename)
	{
	    	byte [] cipher = new byte[PRINTABLE_ASCII];
			   byte  [] plain = new byte[PRINTABLE_ASCII]; 
				for(int i=0;i<plain.length;i++)
				{
					plain[i] += i + SMALLEST_PRINTABLE_ASCII;
				}
				for(int i=0;i<cipher.length;i++)
				{
					cipher[i] += i + SMALLEST_PRINTABLE_ASCII;
				}
				//Shuffled byte array containing key
				shuffle(cipher);
				//Write encrypt key to the file that the user selected
				WriteToFile(cipher, encryptkeyFilename);
				//decrypt key
				for(int i=0;i<plain.length;i++)
				{
					plain[cipher[i] - SMALLEST_PRINTABLE_ASCII] = (byte) (i + SMALLEST_PRINTABLE_ASCII);
				}
				//Write decrypt key to the file that the user selected
				WriteToFile(plain,decryptkeyFilename);
	}
	//Shuffles the byte array for randomizing
	private void shuffle(byte[] arr)
	{
		int arrlength = arr.length;
		for(int i=0;i<arrlength;i++)
		{
			int j = i + (int)(Math.random()* (arrlength-i));
			byte temp = arr[j];
			arr[j] = arr[i];
			arr[i] = temp;
		}
	}	
	
	//Writes encrypted/decrypted keys to the files
	private void WriteToFile(byte[] cipher,File keyFilename){
	BufferedOutputStream out = null;
		try {
		
			out =  new BufferedOutputStream(new FileOutputStream(keyFilename));
			try {
				out.write(cipher,0,cipher.length);
			} 	catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally	{
			try{
		out.close();
			}
			catch(IOException e){
				
			}
		}
	}	

	 public void oneTimePad(File keyFilename,long keySize)
	 {
		 ArrayList<Byte>  key = new ArrayList<Byte>();
		 SecureRandom srand = new SecureRandom();
		 if(keySize == 0)
			 return;
		 //Rounding key size to the next 1024 bytes
		 if(keySize % 1024 != 0)
		 {
			 keySize = (((keySize/1024)) + 1) * 1024;
		 }
		
		 for(int i=0;i<keySize;i++)
		 {
			 key.add((byte) srand.nextInt());
	 
		 }
		 
		 byte[] finalKey = getBytes(key);
		 
		WriteToFile(finalKey, keyFilename);
	 }
	 
	//converts Array list to bytes 
	 private byte[] getBytes(ArrayList<Byte> input)
		{
			byte[] b = new byte[input.size()];
			for(int i =0;i<b.length;i++)
			{
				b[i] = input.get(i);
			}
			return b;
		}
	 
	//write checksum to a file
	 private void WriteString(StringBuilder sb, File file) {
			// TODO Auto-generated method stub
		 DataOutputStream out = null;
				try {
					
					out =  new DataOutputStream(new FileOutputStream(file));
					try {		
						out.write(sb.toString().getBytes());
					} 	catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} 		catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally	{
					try{
				out.close();
					}
					catch(IOException e){
						
					}
				}
			}	
	 //checksum code taken from the following website
	 //http://howtodoinjava.com/core-java/io/how-to-generate-sha-or-md5-file-checksum-hash-in-java/
	 
	 public String readchecksum(File fileName) throws IOException {
		    BufferedReader br = new BufferedReader(new FileReader(fileName));
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null) {
		            sb.append(line);
		            sb.append("\n");
		            line = br.readLine();
		        }
		        return sb.toString();
		    } finally {
		        br.close();
		    }
		}
	 
	 public void getFileChecksum(MessageDigest digest, File infile,File outFile) throws IOException
	 {
	     //Get file input stream for reading the file content
	     FileInputStream fis = new FileInputStream(infile);
	      
	     //Create byte array to read data in chunks
	     byte[] byteArray = new byte[1024];
	     int bytesCount = 0; 
	       
	     //Read file data and update in message digest
	     while ((bytesCount = fis.read(byteArray)) != -1) {
	         digest.update(byteArray, 0, bytesCount);
	     };
	      
	     //close the stream; We don't need it now.
	     fis.close();
	      
	     //Get the hash's bytes
	     byte[] bytes = digest.digest();
	      
	     //This bytes[] has bytes in decimal format;
	     //Convert it to hexadecimal format
	     StringBuilder sb = new StringBuilder();
	     for(int i=0; i< bytes.length ;i++)
	     {
	         sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	     }
	      
	     //return complete hash
	     WriteString(sb,outFile);  
	} 
}
	
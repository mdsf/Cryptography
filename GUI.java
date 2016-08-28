/*##################################################################################################
#############################PROGRAMMED BY MOEID SHARIFF###########################################
###################################################################################################*/
package test1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

@SuppressWarnings("serial")
public class GUI extends JPanel {

	protected static final Exception IOException = null;
	private JFileChooser fc = new JFileChooser();
	private JFrame frmCryptography;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmCryptography.setVisible(true);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private File getFile(boolean isSaveMode, String title){
		int returnVal; //Stores the option selected in the JFileChooser
		File file = null; //Stores the file selected by the user
		fc.setDialogTitle(title); //Set the title of the JFileChooser
		if (isSaveMode) //Saving a file
			returnVal = fc.showSaveDialog(this);
		else //Opening a file
			returnVal = fc.showOpenDialog(this);
		//Check whether the user hit OK
		if (returnVal == JFileChooser.APPROVE_OPTION)
			//Get the file selected by the user
			file = fc.getSelectedFile();
		return file;
	}
	//validates the input as it should only be a number
	private static boolean isNumber(String n)
	{
		try{
			Integer.parseInt(n);
			Long.parseLong(n);
			return true;
			} catch(NumberFormatException nfe){ return false;
		}
	}
	
	private void initialize() {
		frmCryptography = new JFrame();
		frmCryptography.setTitle("Cryptography 1.0");
		frmCryptography.setBounds(100, 100, 450, 290);
		frmCryptography.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCryptography.getContentPane().setLayout(null);
				
		//One time pad encryption/decryption
		JButton btnNewButton_1 = new JButton("One-Timepad Encrypt/Decrypt");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				File keyFile = getFile(false, "Select the file containing your key.");
				File messageFile = getFile(false, "Select the file containing the text to be encrypted/decrypted.");
				//Get the file to store the results
				File outputFile = getFile(false, "Select the file to store the result of your encryption/decryption.");	

				OTP otp = new OTP();
		
				otp.encrypt(messageFile,outputFile,keyFile);
			}
		});
		btnNewButton_1.setBounds(192, 81, 203, 59);
		frmCryptography.getContentPane().add(btnNewButton_1);
		
		//Generates checksum for a file
		JButton btnNewButton_2 = new JButton("MD5 Checksum");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CryptographicUtilities csum = new CryptographicUtilities();
				File insum = getFile(false, "Select a file to generate the checksum for.");
				File outsum = getFile(false, "Select a location to save your checksum.");
				 MessageDigest md5Digest = null;
				try {
					md5Digest = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 try {
					csum.getFileChecksum(md5Digest, insum,outsum);
				} catch (java.io.IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton_2.setBounds(10, 141, 131, 43);
		frmCryptography.getContentPane().add(btnNewButton_2);
		
		//Compares two files checksums
		JButton btnNewButton_3 = new JButton("File Compare");
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CryptographicUtilities csum = new CryptographicUtilities();
				String str1 = null,str2 = null;
				File file1 = getFile(false, "Select the first file containing checksum.");
				File file2 = getFile(false, "Select the second file containing checksum.");
				try {
					str1 = csum.readchecksum(file1);
				} catch (java.io.IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					str2  = csum.readchecksum(file2);
				} catch (java.io.IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
								if(str1.equals(str2)){
				JOptionPane.showMessageDialog(null, "Both the files are the same and have the same checksum");
				JOptionPane.showMessageDialog(null,"Checksum of first file: " + str1 + "Checksum of second file: " + str2);
						}
				if(!(str1.equals(str2))){
				JOptionPane.showMessageDialog(null, "Files are different as they have different checksums");
				JOptionPane.showMessageDialog(null,"Checksum of first file: " + str1 + "Checksum of second file: " + str2);
				}				
			}
		});
		
		btnNewButton_3.setBounds(234, 151, 137, 43);
		frmCryptography.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Cryptanalysis");
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Functionality not implemented");
			}
		});
		btnNewButton_4.setBounds(10, 186, 131, 43);
		frmCryptography.getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("Cipher Key(s)");
		btnNewButton_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
	
		btnNewButton_5.setBounds(10, 11, 131, 54);
		frmCryptography.getContentPane().add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("OTP Key(s)");
		btnNewButton_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_6.addMouseListener(new MouseAdapter() {
			
			@Override
			//Generates one time pad keys
			public void mouseClicked(MouseEvent e) {
				CryptographicUtilities OTP = new CryptographicUtilities();
				String s = JOptionPane.showInputDialog(null,"Enter a positive key size");
				//Input validation if user inputs strings
				while(!isNumber(s))
					s = JOptionPane.showInputDialog(null,"Invalid input. Please enter a positive number");
				long size=0;
				size = Long.parseLong(s);
				//Input Validation if user input negative numbers
				while(size<0){
					s = JOptionPane.showInputDialog(null,"Invalid input. Please enter a positive key size");
					size = Long.parseLong(s);
				}
		
				File ekeyFile = getFile(false, "Select a location to save your encrypt key.");
				OTP.oneTimePad(ekeyFile,size);	
			}
		});
		btnNewButton_6.setBounds(10, 65, 131, 54);
		frmCryptography.getContentPane().add(btnNewButton_6);
				
		//Generates Random Key for substitution
		btnNewButton_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CryptographicUtilities keys = new CryptographicUtilities();
				File ekeyFile = getFile(false, "Select a location to save your encrypt key.");
				File dkeyFile = getFile(false, "Select a location to save your decrypt key.");
				keys.substitutionKeys(ekeyFile, dkeyFile);			
				
			}
		});
		
			//Substitution encryption/decryption
		JButton btnNewButton = new JButton("Substitution Encrypt/Decrypt");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(192, 11, 203, 59);
		frmCryptography.getContentPane().add(btnNewButton);
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				File keyFile = getFile(false, "Select the file containing your key.");
				File messageFile = getFile(false, "Select the file containing the text to be encrypted/decrypted.");
				//Get the file to store the results
				File outputFile = getFile(false, "Select the file to store the result of your encryption/decryption.");	

				Substitution cipher = new Substitution();
				cipher.encrypt(messageFile,outputFile,keyFile);
				
				}
		});
	}
}

 


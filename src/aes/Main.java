package aes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
	
	static Scanner cin = new Scanner(System.in);
	static AESDecryption decrp;
	static BufferedReader buf;
	public static void main (String [] agrs) throws IOException {
		
		char[] encryptedMsg = null;
		int encryptedMsgLen = 0;
		boolean bool=false;
		int switchMode=0;
		String fileName;
		
		while(true)
		{
			System.out.println("\n\nOption\n1.Encryption\n2.Decryption\n3.Exit");
			
			int choice=1;
			choice = cin.nextInt();
			
			if(choice == 1) {
				
				System.out.print("Enter the file name : ");
				
				fileName=cin.nextLine();
				fileName=cin.nextLine();
				
				InputStream is = new FileInputStream(fileName);
				buf = new BufferedReader(new InputStreamReader(is));
				        
				String line = buf.readLine();
				StringBuilder sb = new StringBuilder();
				
				
				while(line != null){
				   sb.append(line).append("\n");
				   line = buf.readLine();
				}
			    
				String fileAsString = sb.toString();
			
				//remove new line character from
				String msg = fileAsString.substring(0,(fileAsString.length()-1));
			
				System.out.println("Block Cipher Modes of Operation\n"
						+ "1.ECB\n2.CBC");
				choice = cin.nextInt();
			
				if(choice==1) {
					
					switchMode=1;
					
					AESEncryption encrp = new AESEncryption(msg,switchMode);
				
					encryptedMsg=encrp.getEncryptedMsg();
				
					encryptedMsgLen=encrp.getEncryptedMsgLen();
				
					bool=true;
				}
			
				else if(choice==2)
				{
					switchMode=2;
					
					AESEncryption encrp = new AESEncryption(msg,switchMode);
				
					encryptedMsg=encrp.getEncryptedMsg();
				
					encryptedMsgLen=encrp.getEncryptedMsgLen();
				
					bool=true;
				}
				
				else System.out.println("Wrong choice!!!");
				
				
			}
			
			else if(choice == 2) {
				
				if(encryptedMsg!=null && bool==true) {
					
					if(switchMode==1)
						decrp= new AESDecryption(encryptedMsg,encryptedMsgLen,switchMode);
					
					else if(switchMode==2)
						decrp = new AESDecryption(encryptedMsg,encryptedMsgLen,switchMode);
					
					else System.out.println("Wrong choice!!!");
				}
				
				else System.out.println("There is no encrypted message!");
			}
			
			else break;
			
		}
		
		
		
		
		
	}
	
}

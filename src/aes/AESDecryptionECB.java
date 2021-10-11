package aes;

import java.util.Scanner;

public class AESDecryptionECB {
	
	Scanner cin = new Scanner(System.in);
	AESOperation aesO = new AESOperation();
	
	public AESDecryptionECB(char[]encryptedMsg,int encryptedMsgLen) {
		
		char[] EDM = new char[encryptedMsgLen];
		int EDMindex=0;
		
		for(int at=0;at<encryptedMsgLen;)
		{
			
			char[][] state = new char[4][4];
			
			for(int k=0;k<4;k++) {
				
				for(int l=0;l<4;l++,at++) {
					
					state[l][k]=encryptedMsg[at];
				
				}
			}
			
			

			aesO.addRound(state,10);
	        for(int i=9;i>=0;i--)
	        {
	        	aesO.invShiftRows(state,1);
	        	aesO.invSubstitute(state);
	        	aesO.addRound(state,i);
	            if(i>0) aesO.mixCLM(state,0);
	        }
	        
	        
	        
	        for(int k=0;k<4;k++) {
				for(int l=0;l<4;l++) {
					
					EDM[EDMindex++]=state[l][k];
					
				}
			}
	        
		}
		
		
		System.out.print("Message : ");
		
		for(int i=0;i<encryptedMsgLen;i++) {
			
			System.out.print(EDM[i]);
		}
		
		System.out.println();
		
	}
	
	
}

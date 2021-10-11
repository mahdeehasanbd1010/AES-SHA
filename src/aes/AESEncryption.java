package aes;

import java.util.Scanner;

public class AESEncryption {
	
Scanner cin = new Scanner(System.in);
	
	AESOperation aesO = new AESOperation();
	char[] encryptedMsg;
	int encryptedMsgLen;
	
	
	public AESEncryption(String msg,int switchMode) {
		
		if(switchMode==1) {
			
			ECBEncryption(msg);
		}
		
		if(switchMode==2) {	
			
			CBCEncryption(msg);
		}
		
		
	}
	
	
	public void ECBEncryption(String msg){
		
		int msgLen=msg.length();
		int paddedMsgLen=msgLen;
		if(msgLen%16!=0) paddedMsgLen=((msgLen/16)+1)*16;
		
		char[] paddedMsg = new char[paddedMsgLen];
		char[] EDM = new char[paddedMsgLen];
		int EDMindex=0;
		
		
		//padding with 0
		for(int i=0;i<paddedMsgLen;i++) {
			if(msgLen<=i) paddedMsg[i]=0;
			else paddedMsg[i]=msg.charAt(i);
		}
		
		for(int at=0;at<paddedMsgLen;)
		{
			
			char[][] state = new char[4][4];
			for(int k=0;k<4;k++) {
				for(int l=0;l<4;l++,at++) {
					
					state[l][k]=paddedMsg[at];
				
				}
			}
			
			aesO.addRound(state,0);
			
			
	        for(int i=1; i<11; i++)
	        {
	            state=aesO.substitute(state);
	            
	            aesO.shiftRow(state,1);
	            
				if(i<10) aesO.mixCLM(state,1);
	            
				aesO.addRound(state,i);
	            
	            
	        }
	        
	        for(int k=0;k<4;k++) {
				for(int l=0;l<4;l++) {
					
					EDM[EDMindex++]=state[l][k];
					
				}
			}
	        
		}
		
		encryptedMsg=EDM;
		encryptedMsgLen=paddedMsgLen;
		
		String hex;
		
		System.out.println("Encrypted message in Hex : ");
		
		for (int i = 0; i < paddedMsgLen; i++) {
			 
			 hex = String.format("%02x ", (int) EDM[i]);
			 System.out.print(hex);
		}
		 
		System.out.println();
		
		
	}
	
	
	
	
	
	
	
	public void CBCEncryption(String msg) {

		char[][] IV = new char[4][4];
		String initializationVectorStr="abcdefghijklmnop";
		
		int a=0;
		for(int i=0;i<4;i++) {
		
			for(int j=0;j<4;j++) {
			
				IV[j][i]=initializationVectorStr.charAt(a++);
			}
			
				if(initializationVectorStr.length()==a) break;
		}
		
		
		int msgLen=msg.length();
		int paddedMsgLen=msgLen;
		if(msgLen%16!=0) paddedMsgLen=((msgLen/16)+1)*16;
		
		char[] paddedMsg = new char[paddedMsgLen];
		char[] EDM = new char[paddedMsgLen];
		int EDMindex=0;
		
		for(int i=0;i<paddedMsgLen;i++) {
			if(msgLen<=i) paddedMsg[i]=0;
			else paddedMsg[i]=msg.charAt(i);
		}
		
		for(int at=0;at<paddedMsgLen;)
		{
			
			char[][] state = new char[4][4];
			for(int k=0;k<4;k++) {
				for(int l=0;l<4;l++,at++) {
					
					state[l][k]=paddedMsg[at];
				
				}
			}
			
			for(int k=0;k<4;k++) {
				for(int l=0;l<4;l++) {
				
					state[l][k]^=IV[l][k];
			
				}
			}
			
			
			aesO.addRound(state,0);
			
			
	        for(int i=1; i<11; i++)
	        {
	            state=aesO.substitute(state);
	            
	            aesO.shiftRow(state,1);
	            
				if(i<10) aesO.mixCLM(state,1);
	            
				aesO.addRound(state,i);
	            
	            
	        }
	        
	        for(int k=0;k<4;k++) {
				for(int l=0;l<4;l++) {
					
					EDM[EDMindex++]=state[l][k];
					
				}
			}
	        
	        for(int k=0;k<4;k++) {
				for(int l=0;l<4;l++) {
				
					IV[l][k]=state[l][k];
			
				}
			}
	        
	        
		}
		
		encryptedMsg=EDM;
		encryptedMsgLen=paddedMsgLen;
		
		String hex;
		
		System.out.println("Encrypted message in Hex : ");
		
		for (int i = 0; i < paddedMsgLen; i++) {
			 
			 hex = String.format("%02x ", (int) EDM[i]);
			 System.out.print(hex);
		}
		 
		System.out.println();
		
		
		
	}
	
	public char[] getEncryptedMsg() {
		
		return encryptedMsg;
	}
	
	public int getEncryptedMsgLen() {
		
		return encryptedMsgLen;
	}
	
	
}

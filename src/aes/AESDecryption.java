package aes;

import java.util.Scanner;

public class AESDecryption {
	
	Scanner cin = new Scanner(System.in);
	AESOperation aesO = new AESOperation();
	
	public AESDecryption(char[]encryptedMsg,int encryptedMsgLen,int switchMode) {
		
		if(switchMode==1) {
			
			ECBDecryption(encryptedMsg, encryptedMsgLen);
		}
		
		
		if(switchMode==2) {
			
			CBCDecryption(encryptedMsg, encryptedMsgLen);
		}
		
		
		
	}
	
	
	
	public void ECBDecryption(char[]encryptedMsg,int encryptedMsgLen) {
		
		
		
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
	        	state=aesO.invSubstitute(state);
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
	
	
	
	
	
	
	
	public void CBCDecryption(char[]encryptedMsg,int encryptedMsgLen) {
		
		char[][] IV = new char[4][4];
		String initializationVectorStr="abcdefghijklmnop";
		int a=0;
		for(int i=0;i<4;i++) {
		
			for(int j=0;j<4;j++) {
			
				IV[j][i]=initializationVectorStr.charAt(a++);
			}
		
			if(initializationVectorStr.length()==a) break;
		}
		
		
		
		char[] EDM = new char[encryptedMsgLen];
		int EDMindex=0;
		
		for(int at=0;at<encryptedMsgLen;)
		{
			
			char[][] tempIV = new char[4][4];
			
			char[][] state = new char[4][4];
				
			for(int k=0;k<4;k++) {
				for(int l=0;l<4;l++) {
				
					tempIV[l][k]=state[l][k];
			
				}
			}
			
			
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
					
					state[l][k]^=IV[l][k];
				
				}
			}
	        
	        
	        for(int k=0;k<4;k++) {
				for(int l=0;l<4;l++) {
					
					EDM[EDMindex++]=state[l][k];
					
				}
			}
	        
	        if(at>15) {
	        	
	        	for(int k=0;k<4;k++) {
					for(int l=0;l<4;l++) {
						
						IV[l][k]=tempIV[l][k];
					
					}
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



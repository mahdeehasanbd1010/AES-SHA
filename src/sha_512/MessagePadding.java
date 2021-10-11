package sha_512;

import java.math.BigInteger;

public class MessagePadding {
	
	public byte[] padding(byte []input) {
		
		int size =input.length+17;
		
		while(size%128 != 0) {
			
			size++;
		}
		
		byte [] output = new byte[size];
		
		for(int i=0 ; i<input.length ; i++) {
			
			output[i]=input[i];
			
		}
		
		output[input.length] = (byte) 0x80;
		
		byte[] paddingByte = BigInteger.valueOf(input.length * 8).toByteArray();

		
		for (int i = paddingByte.length; i > 0; i--) {
			
			output[size - i] = paddingByte[paddingByte.length - i];
		}
		
		return output;
		
	}
	
	public long helperOFBlock(byte [] input , int j) {
		
		long value=0;
		
		for (int i = 0; i < 8; i++) {
			
			value = (value << 8) + (input[i + j] & 0xff);
			
		}
		return value;
	}
	
	public long[][] block_function(byte [] input) {
		
		long [][]block = new long[input.length/128][16];
		
		for(int i=0 ; i<input.length/128 ; i++) {
			
			for(int j=0 ; j<16 ; j++) {
				
				block[i][j] = helperOFBlock(input, i * 128 + j * 8);
				
			}
		}
		
		return block;
		
	}
	
	public long [][] processMessage(long  [][]in){
		
		long [][]word = new long[in.length][81];
		
		RoundFunction rF = new RoundFunction();
		
		for(int i=0 ; i<in.length ; i++) {
			
			for(int j=0 ; j<16 ; j++) {
				
				word[i][j]=in[i][j];
			}
			
			for(int j=16 ; j<80 ; j++) {
				
				word[i][j]=  rF.sigma1To512(word[i][j-2]) + word[i][j-7] + rF.sigma0To512(word[i][j-15])+word[i][j-16];
			} 
		}
		
		return word;
		
	}
}

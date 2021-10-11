package aes;

public class AESOperation {
	
	char[][] expandedKey = new char[4][44];
	char[][] sBox = new char[16][16];
	char[][] inverseSBox = new char[16][16];
	char[][] key = new char[4][4];
	int[][] FMCA = new int[][]{{2,3,1,1},{1,2,3,1},{1,1,2,3},{3,1,1,2}};
	int[][] BMCA = new int[][]{{14,11,13,9},{9,14,11,13},{13,9,14,11},{11,13,9,14}};
	
	public AESOperation() {
		
		initializeSBox();
		initializeInverseSBox();
		initializeKey();
		expansionKey();
	}
	
	
	public void initializeKey() {
		
		String keyStr= "abcdefghijklmnop";
		int k=0;
		for(int i=0; i<4; i++)
		{
			for(int j=0; j<4; j++)
			{
	        
				key[j][i] = keyStr.charAt(k++);
			}
			if(k==keyStr.length()) break;
		}
		
	}
	
	
	public char[] substituteWord(char temp[])
	{
	    char ch;
	    
	    for(int i=0; i<4; i++)
	    {
	        char ch2=15;
	    	ch = temp[i];
	        ch2&=ch;
	        temp[i] = sBox[(int)(ch>>4)][(int)(ch2)];
	        
	    }
	    
	    return temp;
	}
	
	
	public char[] RotateWord(char temp[])
	{
	    char ch = temp[0];
	    for(int i=0;i<3; i++) temp[i] = temp[i+1];
	    temp[3] = ch;
	    return temp;
	}
	
	public char[] Rcon(int round)
	{
	    char[] temp = new char[4];
	    if(round<9) temp[0] = (char) Math.pow(2,round-1);
	    else if(round==9) temp[0] = 27;
	    else temp[0] = 54;
	    for(int i=1;i<4; i++) temp[i] = 0;
	    return temp;
	}
	
	
	public char[] X_OR(char temp1[],char temp2[])
	{
	    for(int i=0; i<4; i++) temp1[i] = (char)((int)temp1[i]^(int)temp2[i]);
	    return temp1;
	}
	
	public void expansionKey()
	{
	    for(int word=0;word<4;word++)
	        for(int b=0;b<4;b++) expandedKey[b][word] = key[b][word];

	    char[] temp= new char[4];
	    
	    for(int word=4; word<44;word++)
	    {
	        for(int b=0;b<4; b++) temp[b] = expandedKey[b][word-1];
	        if(word%4==0) temp = X_OR(substituteWord (RotateWord (temp)),Rcon(word/4));
	        for(int b=0; b<4; b++) expandedKey[b][word] = (char) (temp[b]^expandedKey[b][word-4]);

	    }
	}
	
	
	public void initializeSBox() {
		
		String sBoxStr=	  "637c777bf26b6fc53001672bfed7ab76"
						+ "ca82c97dfa5947f0add4a2af9ca472c0"
						+ "b7fd9326363ff7cc34a5e5f171d83115"
						+ "04c723c31896059a071280e2eb27b275"
						+ "09832c1a1b6e5aa0523bd6b329e32f84"
						+ "53d100ed20fcb15b6acbbe394a4c58cf"
						+ "d0efaafb434d338545f9027f503c9fa8"
						+ "51a3408f929d38f5bcb6da2110fff3d2"
						+ "cd0c13ec5f974417c4a77e3d645d1973"
						+ "60814fdc222a908846eeb814de5e0bdb"
						+ "e0323a0a4906245cc2d3ac629195e479"
						+ "e7c8376d8dd54ea96c56f4ea657aae08"
						+ "ba78252e1ca6b4c6e8dd741f4bbd8b8a"
						+ "703eb5664803f60e613557b986c11d9e"
						+ "e1f8981169d98e949b1e87e9ce5528df"
						+ "8ca1890dbfe6426841992d0fb054bb16";
		
		
		for(int i=0,j=0,k=0;i<sBoxStr.length() && j<16 ;i+=2) {
			
			String str = sBoxStr.substring(i, i+2);
			sBox[j][k++]=(char)Integer.parseInt(str, 16);
			
			if(k==16) {
				k=0;
				j++;
			}
			
		}
	}
	
	
	public void initializeInverseSBox() {
		
		String inverseSBoxStr=    "52096ad53036a538bf40a39e81f3d7fb"
								+ "7ce339829b2fff87348e4344c4dee9cb"
								+ "547b9432a6c2233dee4c950b42fac34e"
								+ "082ea16628d924b2765ba2496d8bd125"
								+ "72f8f66486689816d4a45ccc5d65b692"
								+ "6c704850fdedb9da5e154657a78d9d84"
								+ "90d8ab008cbcd30af7e45805b8b34506"
								+ "d02c1e8fca3f0f02c1afbd0301138a6b"
								+ "3a9111414f67dcea97f2cfcef0b4e673"
								+ "96ac7422e7ad3585e2f937e81c75df6e"
								+ "47f11a711d29c5896fb7620eaa18be1b"
								+ "fc563e4bc6d279209adbc0fe78cd5af4"
								+ "1fdda8338807c731b11210592780ec5f"
								+ "60517fa919b54a0d2de57a9f93c99cef"
								+ "a0e03b4dae2af5b0c8ebbb3c83539961"
								+ "172b047eba77d626e169146355210c7d";

		for(int i=0,j=0,k=0;i<inverseSBoxStr.length() && j<16 ;i+=2) {
			
			String str = inverseSBoxStr.substring(i, i+2);
			inverseSBox[j][k++]=(char)Integer.parseInt(str, 16);
			
			if(k==16) {
				k=0;
				j++;
			}
			
		}
	}
	
	
	public void addRound(char[][]state,int round)
	{
	    for(int i=0; i<4; i++)
	        for(int j=0; j<4; j++)
	    {
	        state[j][i] = (char) (state[j][i]^expandedKey[j][(round*4)+i]);
	    }
	}
	
	
	
	
	public void shiftRow(char[][] msg,int startinRow)
	{
		
	    if(startinRow==4) return;
	    char[] ch = new char[3];
	    for(int i=0;i<startinRow; i++) ch[i] = msg[startinRow][i];
	    for(int i=0; i<4; i++)
	    {
	        int clm = (i+startinRow)%4;
	        if(clm>=i) msg[startinRow][i] = msg[startinRow][clm];
	        else msg[startinRow][i] = ch[clm];
	    }
	    shiftRow(msg,++startinRow);
	}
	
	public void invShiftRows(char[][] msg,int startinRow)
	{
	    if(startinRow==4) return;
	    char[] ch = new char[3];
	    for(int i=0;i<startinRow; i++) ch[i] = msg[startinRow][3-i];
	    for(int i=0; i<4; i++)
	    {
	        int clm = (i+startinRow)%4;
	        if(clm>=i) msg[startinRow][3-i] = msg[startinRow][3-clm];
	        else msg[startinRow][3-i] = ch[clm];
	    }
	    invShiftRows(msg,++startinRow);
	}
	
	
	public char[][] substitute(char[][] state)
	{
	    
	    for(int i=0; i<4; i++)
	        for(int j=0; j<4; j++)
	        {
	         
	    	    char ch2=15;
	    	    char ch = state[j][i];
	    	    ch2&=ch;
	    	    state[j][i] = sBox[(int)(ch>>4)][(int)(ch2)];    
	    	
	        }
	    
	    return state;
	}

	public char[][] invSubstitute(char[][] state)
	{
		char ch;
	    for(int i=0; i<4; i++)
	        for(int j=0; j<4; j++)
	        {
	        	
	    	    char ch2=15;
	    	    ch = state[j][i];
	    	    ch2&=ch;
	    	    state[j][i] = inverseSBox[(int)(ch>>4)][(int)(ch2)];    
	    	    
	        }
	    return state;
	}
	
	
	
	
	public char product(char ch)
	{
		if(ch>>7!=0) 
		{	
			ch^=128;
			return (char) ((ch<<1)^(27));
		}
		return (char) (ch<<1);
	}
	
	public char prdctBy(int n,char ch)
	{
		if(n==1) return ch;
	    if(n==2) return product(ch);
	    else return prdctBy(2,prdctBy(n/2,ch));
	}
	
	
	public char gfProduct(int n,char ch)
	{
	    char tempCh = 0;
	    int power;
	    
	    while(n!=0)
	    {
	    	power = (int) (Math.log10(n)/Math.log10(2));
	        tempCh^=prdctBy((int)Math.pow(2,power),ch);
	        n = n%(int)(Math.pow(2,power));
	    }
	    
	    
	    return tempCh;
	    
	}
	
	
	void mixCLM(char[][] msg,int isForward)
	{
		
	    for(int i=0; i<4; i++)
	    {
	    	
	    	char[] tempArr=new char[4];
	        for(int k=0; k<4; k++)
	        {
	        	tempArr[k] = msg[k][i];
	            msg[k][i] = 0;
	        }
	        
	        if(isForward==1) 
	        {	
	        	for(int j=0; j<4; j++) 
	        	{	
	        		for(int k = 0; k<4; k++) 
	        		{
	        			msg[j][i]^=gfProduct(FMCA[j][k],tempArr[k]);
	        			
	        		}	
	        	}
	        }
	        
	        else 
	        {	
	        	for(int j=0; j<4; j++)
	        	{	
	        		for(int k = 0; k<4; k++)
	        		{	
	        			msg[j][i]^=gfProduct(BMCA[j][k],tempArr[k]);
	        		}
	        	}
	        }
	    }
	}
	
	
}

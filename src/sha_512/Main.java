package sha_512;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	
	static Scanner cin = new Scanner(System.in);
	
	public static void main(String[] agrs) {
		
		String fileName;
		
		System.out.print("Enter the file name : ");
		
		fileName = cin.nextLine();
		
		File file = new File(fileName);
        
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            
            byte[] fileContent = new byte[(int)file.length()];
            
            fin.read(fileContent);
            
            RoundFunction rF = new RoundFunction();
            String result=rF.hash(fileContent);
            
            String s = new String(fileContent);
            System.out.println("Message : " + s);
            System.out.println("Hash value in hex : " + result);
        
        }
        
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }
        finally {
            
        	try {
                if (fin != null) {
                    fin.close();
                }
            }
            catch (IOException ioe) {
                System.out.println(ioe);
            }
        }
	} 
}

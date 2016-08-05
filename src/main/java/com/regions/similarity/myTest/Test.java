package com.regions.similarity.myTest;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class Test 
{
    public static void main( String[] args )
    {
    	HigherOrderBoVW readG1 = new HigherOrderBoVW();
    	HigherOrderBoVW readG2 = new HigherOrderBoVW();
    	HigherOrderBoVW readG3 = new HigherOrderBoVW();
    	
    	try {
    		
			readG1.readLines("src/test/resources/G1.txt", "a");
			readG2.readLines("src/test/resources/G2.txt", "a");
			readG3.readLines("src/test/resources/G3.txt", "a");
			
			readG1.readLines("src/test/resources/region.txt", "r");
			readG2.readLines("src/test/resources/region.txt", "r");
			readG3.readLines("src/test/resources/region.txt", "r");
			
			readG1.calculate();
			readG2.calculate();
			readG3.calculate();
			
			System.out.println("\n\n");
			
			for(int i = 0; i < readG1.getR().size(); i++){
				
//				int p1O = readG1.getO().get(i);
//				int p2O = readG2.getO().get(i);
//				int p3O = readG3.getO().get(i);
				
				int sum = readG1.getO().get(i) + readG2.getO().get(i) + readG3.getO().get(i);
				
				float p1 = (float) readG1.getO().get(i) / (float) sum;
				float p2 = (float) readG2.getO().get(i) / (float) sum;
				float p3 = (float) readG3.getO().get(i) / (float) sum;
				
				System.out.println("R" + i + ": " + readG1.getR().get(i));
				readG1.writeLine("src/test/resources/replace.txt", "R" + i + ": " + readG1.getR().get(i) + "\n");
				
				System.out.println("G1 " + p1 + "; G2 " + p2 + "; G3 " + p3);
				readG1.writeLine("src/test/resources/replace.txt", "G1 " + p1 + "; G2 " + p2 + "; G3 " + p3 + "\n");
				
				System.out.println("\n");
				readG1.writeLine("src/test/resources/replace.txt", "\n");
			}
			
			//readG1.writeLine("src/test/resources/replace.txt", "AAA");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
}

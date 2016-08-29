package com.regions.similarity.myTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Test_2 {
	
	public static List<String> str = new ArrayList<String>(); 	
	public static List<String> rs = new ArrayList<String>();
	public static List<Integer> finalV = new ArrayList<Integer>();
	
	public static void main(String[] args) throws IOException {
		//建立pair wise
		
		String pathR1 = "src/test/resources/G1.txt";
		String pathR2 = "src/test/resources/G2.txt";
		String pathR3 = "src/test/resources/G3.txt";
//		String pathR1 = "src/test/resources/R1.txt";
//		String pathR2 = "src/test/resources/R2.txt";
//		String pathR3 = "src/test/resources/R3.txt";
		//R1.txt is for replaced regions
		//G1.txt is for original regions
		
		readLines(pathR1);
		readLines(pathR2);
		readLines(pathR3);
		System.out.println("There are " + str.size() + " data");
		for(String s : str){
			System.out.println(s);
		}
		System.out.println("===========================");
		
		
		String writePath = "src/test/resources/final.txt";
		deleteFile(writePath);
		
		initial();

		
//		String strr = " regionRect = default;Chocolate";	
//		createFinalV(strr);
//		System.out.println("===========================");
//		printList(finalV);
//		writeLine(writePath, finalV.toString().replace("[", "").replace("]", ""));
		
		
		for(String twodstring : str){
			
			String xstring = twodstring.split(",")[0];
			
			System.out.println(xstring);
			
			createFinalV(xstring);
			printList(finalV);
			
			writeLine(writePath, finalV.toString().replace("[", "").replace("]", ""));
			
			System.out.println("===========================");
			
		}
		
		
	}

	
	public static void createFinalV(String xString){
		finalV = new ArrayList<Integer>();
		
		List<Integer> tempV;
		for(int i = 0; i < rs.size(); i++){
			
			tempV = oneIteration(xString, rs.get(i));
			printList(tempV);
			finalV.addAll(tempV);
			
		}
			
	}
	
	
	public static List<Integer> oneIteration(String xStr, String r){
		
		System.out.println("String + " + xStr + "; r = " + r);
		
		List<Integer> occ = new ArrayList<Integer>();
		int[] temp = new int[rs.size()];
		
		for(int i = 0; i < rs.size(); i++){
			temp[i] = 0;
		}
		
		String[] t = xStr.replace("=", "<").split("<");
		
		int index = -1;
		for(int i = 0; i < t.length; i++){
			
			String tt = t[i];
			System.out.println("index = " + i + "; t[i] = " + tt);
			if(t[i].contains(r)){
				index = i;
				break;
			}			
		}
		
		System.out.println("Index = " + index);
		
		if(index == (t.length - 1) || index == -1){

			for(int i = 0; i < rs.size(); i++){
				occ.add(temp[i]);
			}
			return occ;
		}
		
		
		for(int i = index + 1; i < t.length; i++){
			System.out.println("index = " + i + "; t[i] = " + t[i]);
			int updateIndex = findIndex(t[i]);
			temp[updateIndex] = temp[updateIndex] + 1;
			
		}
		
		for(int i = 0; i < rs.size(); i++){
//			occ = new ArrayList<Integer>();
			occ.add(temp[i]);
		}
		
		return occ;
	}
	
	
	public static int findIndex(String r){
		int index = -1;
		
		for(int i = 0; i < rs.size(); i++){
			if(r.contains(rs.get(i))){
				return i;
			}
		}
		
		return index;
	}
	
	
	public static void readLines(String path) throws IOException{
		
		String line;
		
		try(
				InputStream fis = new FileInputStream(path);
				InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
				BufferedReader br = new BufferedReader(isr);
		){
			while((line = br.readLine()) != null){
				str.add(line);
				
			}
		}	
		
	}
	
	
	public static void writeLine(String path, String line) throws IOException {
		
		try{

				File file = new File(path);
				
				if (!file.exists()) {
					file.createNewFile();
				}
				
				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(line);
				bw.newLine();
				bw.close();
				
		}catch (Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	
	public static void deleteFile(String path){
		
		try{
			File file = new File(path);
			if(file.exists()){
				file.delete();
				System.out.println("\nFile " + path + " is deleted \n");
			}else{
				System.out.println("\nFile " + path + " cannot find \n");
			}
		}catch (Exception e){
			System.out.println("Cannot delete file " + path);
		}
	}
	
	
	private static void printList(List<Integer> e) {
		
		for(int i = 0; i < e.size(); i++){
			System.out.print("[");
			System.out.print(" " + e.get(i));
			System.out.print(" ]");
		}
		System.out.println();
	}
	
	
	private static void initial(){
		rs.add("PolygonFive;Blue");
		rs.add("PolygonSix;Red");
		rs.add("PolygonEight;Chartreuse");
		rs.add("default;Chocolate");
		
		rs.add("Triangle;Coral");
		rs.add("Rectangle;Crimson");
		rs.add("Trapezoidal;Green");
		rs.add("Moon;DarkGray");
		
		rs.add("Ellipse;DarkBlue");
		rs.add("Halfcircle;Pink");
		rs.add("Quarter;Goldenrod");
		rs.add("QuarterMiss;Lavender");
		
//		PolygonFive;Blue
//		PolygonSix;Red
//		PolygonEight;Chartreuse
//		default;Chocolate
		
//		Triangle;Coral
//		Rectangle;Crimson
//		Trapezoidal;Green
//		Moon;DarkGray
		
//		Ellipse;DarkBlue
//		Halfcircle;Pink
//		Quarter;Goldenrod
//		QuarterMiss;Lavender

//		rs.add("PolygonFive;Blue");
//		rs.add("PolygonSix;Red");
//		rs.add("regionPoly");
//		rs.add("regionRect");
//		rs.add("Rectangle;Crimson");
//		rs.add("Trapezoidal;Green");
//		rs.add("Ellipse;DarkBlue");
//		rs.add("regionQuar");
//		rs.add("QuarterMiss;Lavender");
		
//		rs.add("A");
//		rs.add("B");
//		rs.add("C");
//		rs.add("D");
//		rs.add("E");
//		rs.add("F");
	}
	
	
}

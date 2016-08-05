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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HigherOrderBoVW {
	
	private List<String> a = new ArrayList<String>();	
	private List<String> r = new ArrayList<String>();
	
	private List<String> aReplaced = new ArrayList<String>();
	private List<String> rReplaced = new ArrayList<String>();
	
	private List<Integer> o = new ArrayList<Integer>();
	
	public HigherOrderBoVW(){
		a = new ArrayList<String>();
		r = new ArrayList<String>();
		setO(new ArrayList<Integer>());
		aReplaced = new ArrayList<String>();
		rReplaced = new ArrayList<String>();
	}
	
	public void calculate(){
		
//		for(int i = 0; i < r.size(); i++){
//			System.out.println(a.get(i) + "\n");
//		}
		System.out.println("\n");
		
		int count = 0;
		for(String ri : r){
			for(String ai : a){
				
				//int n = numberOfOccurrance(ai, ri);
				Pattern p = Pattern.compile(ri);
				int n = countMatches(p, ai);
				
				count = count + n;
				
			}
			getO().add(count);
			count = 0;
		}
		
		for(int i = 0; i < r.size(); i++){
			System.out.println(r.get(i) + ": " + getO().get(i));
		}
		
	}

	
	private int countMatches(Pattern pattern, String string)
	{
	    Matcher matcher = pattern.matcher(string);

	    int count = 0;
	    int pos = 0;
	    while (matcher.find(pos))
	    {
	        count++;
	        pos = matcher.start() + 1;
	    }

	    return count;
	}
	
	public void readLines(String path, String flag) throws IOException{
		
		String line;
		
		try(
				InputStream fis = new FileInputStream(path);
				InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
				BufferedReader br = new BufferedReader(isr);
		){
			while((line = br.readLine()) != null){
				
				if(flag.equals("a")){
					a.add(line);
				}else if(flag.equals("r")){
					r.add(line);
				}else {
					System.out.println("Flag is incorrect");
				}
				
			}
		}
		
		
	}
	
	
	public void writeLine(String path, String line) throws IOException {
		
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
	
	
	public void writeNew2DString(String path) throws IOException{
		
		for(String str : aReplaced){
			writeLine(path, str);
		}
		
	}
	
	public void writeNewRegions(String path) throws IOException{
		
		for(String r : rReplaced){
			writeLine(path, r);
		}
		
	}
	
	
	public void replace2DString(String str, List<String> target){
		
		String temp = "";
		
		System.out.println("Target: " + target.toString());
		
		for(String s : a){
			
			System.out.println(s);
			temp = replace(s, str, target);
			aReplaced.add(temp);
			
		}
		
		System.out.println("\n=\n");
	}
	
	public String replace(String originalTwoDString, String str, List<String> target){
		
		String returnString = originalTwoDString;
		
		System.out.println(originalTwoDString);
		
		for(String r : target){
			
			System.out.println(str + " repalce " + r);
			returnString = returnString.replace(r, str);
			
		}
		
		System.out.println("New: " + returnString);
		
		return returnString;
	}
	
	
	public void replaceRegion(String region, List<String> regions){
		
		for(String t : r){
			
			if(regions.contains(t)){
				
				if(rReplaced.contains(region)){
					
				}else{
					rReplaced.add(region);
				}
				
			}else{
				rReplaced.add(t);
			}
				
			
		}
		
	}
	

	public List<String> getA() {
		return a;
	}

	public void setA(List<String> a) {
		this.a = a;
	}

	public List<String> getR() {
		return r;
	}

	public void setR(List<String> r) {
		this.r = r;
	}

	public List<Integer> getO() {
		return o;
	}

	public void setO(List<Integer> o) {
		this.o = o;
	}

	public List<String> getaReplaced() {
		return aReplaced;
	}

	public void setaReplaced(List<String> aReplaced) {
		this.aReplaced = aReplaced;
	}

	public List<String> getrReplaced() {
		return rReplaced;
	}

	public void setrReplaced(List<String> rReplaced) {
		this.rReplaced = rReplaced;
	}


	
}

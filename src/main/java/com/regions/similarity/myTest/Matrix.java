package com.regions.similarity.myTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Matrix {

	private List<String> regions = new ArrayList<String>();
	private List<String> regionsReplaced = new ArrayList<String>();
	
	private List<List<Integer>> cooccurrance = new ArrayList<List<Integer>>();
	
	public Matrix(List<String> r){
		setRegions(r);
		setRegionsReplaced(regions);
		setCooccurrance(new ArrayList<List<Integer>>());
	}
	
	
	public void createCooccurranceMatrix(List<String> regions){
		
		System.out.println(regionsReplaced);
		
		for(String i : regions){
			
			createCooccurranceVector(i);
			
		}
		
	}
	
	
	public void createCooccurranceVector(String i){
		
		List<Integer> oneVector = new ArrayList<Integer>(); 
		System.out.println(i);
		
		for(String r : regionsReplaced){
			
			Pattern regionPattern = Pattern.compile(r);
			int n = countMatches(regionPattern, i);
			oneVector.add(n);
					
		}
		System.out.println(oneVector.toString());
		getCooccurrance().add(oneVector);
	}
	
	
	public void replaceRegions(String t, List<String> rs){
		
		List<String> returnList = new ArrayList<String>();
		
		for(String str : getRegionsReplaced()){
			
			if(rs.contains(str)){
				
				returnList.add(t);
				
			}else{
				returnList.add(str);
			}
			
		}
		
		System.out.println("\n");
		for(String temp : getRegionsReplaced()){

			System.out.print(temp + "-");
			
		}
		System.out.println();
		
		setRegionsReplaced(returnList);
		
		removeDuplicateR();
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
	
	private void removeDuplicateR(){
		
		List<String> list = regionsReplaced;
		regionsReplaced = new ArrayList<String>();
		
		for(String str : list){
			
			if(regionsReplaced.contains(str)){
				
			}else{
				regionsReplaced.add(str);
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
	
	
	
	public List<String> getRegions() {
		return regions;
	}

	public void setRegions(List<String> regions) {
		this.regions = regions;
	}


	public List<String> getRegionsReplaced() {
		return regionsReplaced;
	}


	public void setRegionsReplaced(List<String> regionsReplaced) {
		this.regionsReplaced = regionsReplaced;
	}


	public List<List<Integer>> getCooccurrance() {
		return cooccurrance;
	}


	public void setCooccurrance(List<List<Integer>> cooccurrance) {
		this.cooccurrance = cooccurrance;
	}
	
	
	
	
}

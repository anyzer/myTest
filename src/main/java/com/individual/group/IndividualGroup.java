package com.individual.group;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.regions.similarity.myTest.HigherOrderBoVW;
import com.regions.similarity.myTest.Matrix;
import com.regions.similarity.myTest.Util;

public class IndividualGroup {
		
	public static HigherOrderBoVW myTwoDStringG1 = new HigherOrderBoVW();
	public static HigherOrderBoVW myTwoDStringG2 = new HigherOrderBoVW();
	public static HigherOrderBoVW myTwoDStringG3 = new HigherOrderBoVW();

	public static List<String> poly = new ArrayList<String>();
	public static List<String> rect = new ArrayList<String>();
	public static List<String> quar = new ArrayList<String>();
		
	public static List<List<Double>> g1Vectors = new ArrayList<List<Double>>();
	public static List<List<Double>> g2Vectors = new ArrayList<List<Double>>();
	public static List<List<Double>> g3Vectors = new ArrayList<List<Double>>();
	
	public static List<List<Double>> g1VectorsNorm = new ArrayList<List<Double>>();
	public static List<List<Double>> g2VectorsNorm = new ArrayList<List<Double>>();
	public static List<List<Double>> g3VectorsNorm = new ArrayList<List<Double>>();
	
	public static List<List<Double>> centroidVectors = new ArrayList<List<Double>>();
	public static List<List<Double>> centroidVectorsNorm = new ArrayList<List<Double>>();
	
	public static int numberOfImagesCorrectlyGrouped = 0;
	
	public static void main(String[] args) throws IOException {

//		poly.add("PolygonSix;Red");// 1
		poly.add("PolygonEight;Chartreuse");// 0.778
		poly.add("default;Chocolate");// 0.774

		rect.add("Triangle;Coral");// 0.75
//		rect.add("Rectangle;Crimson");// 0.9285synthetic
		rect.add("Moon;DarkGray");// 0.7894

		quar.add("Halfcircle;Pink");// 0.9
		quar.add("Quarter;Goldenrod");// 0.8965
//		quar.add("QuarterMiss;Lavender");// 1
		
		
		myTwoDStringG1.readLines("src/test/resources/G1.txt", "a");
		myTwoDStringG1.replace2DString("regionPoly", poly);
		myTwoDStringG1.writeNew2DString("src/test/resources/group1Replaced.txt");
		myTwoDStringG1.writeNew2DString("src/test/resources/groupReplaced.txt");
		myTwoDStringG1.readLines("src/test/resources/region.txt", "r");
		// myTwoDStringG1.replaceRegion("regionOne", poly);

		myTwoDStringG2.readLines("src/test/resources/G2.txt", "a");
		myTwoDStringG2.replace2DString("regionRect", rect);
		myTwoDStringG2.writeNew2DString("src/test/resources/group2Replaced.txt");
		myTwoDStringG1.writeNew2DString("src/test/resources/groupReplaced.txt");
		myTwoDStringG2.readLines("src/test/resources/region.txt", "r");
		// myTwoDStringG2.replaceRegion("regionTwo", rect);

		myTwoDStringG3.readLines("src/test/resources/G3.txt", "a");
		myTwoDStringG3.replace2DString("regionQuar", quar);
		myTwoDStringG3.writeNew2DString("src/test/resources/group3Replaced.txt");
		myTwoDStringG1.writeNew2DString("src/test/resources/groupReplaced.txt");
		myTwoDStringG3.readLines("src/test/resources/region.txt", "r");
		// myTwoDStringG3.replaceRegion("regionThree", quar);
		
		
		
		Matrix mG1 = new Matrix(myTwoDStringG1.getR());
		mG1.replaceRegions("regionPoly", poly);
		mG1.replaceRegions("regionRect", rect);
		mG1.replaceRegions("regionQuar", quar);
		
		Matrix mG2 = new Matrix(myTwoDStringG1.getR());
		mG2.replaceRegions("regionPoly", poly);
		mG2.replaceRegions("regionRect", rect);
		mG2.replaceRegions("regionQuar", quar);
		
		Matrix mG3 = new Matrix(myTwoDStringG1.getR());
		mG3.replaceRegions("regionPoly", poly);
		mG3.replaceRegions("regionRect", rect);
		mG3.replaceRegions("regionQuar", quar);
		
		mG1.createCooccurranceMatrix(myTwoDStringG1.getaReplaced());
		System.out.println("-----------------------------------------------------");
		mG2.createCooccurranceMatrix(myTwoDStringG2.getaReplaced());
		System.out.println("-----------------------------------------------------");
		mG3.createCooccurranceMatrix(myTwoDStringG3.getaReplaced());
		System.out.println("-----------------------------------------------------");
		
		
		
//		createWekaData("src/test/resources/wekaDataG1.txt", mG1);
//		createWekaData("src/test/resources/wekaDataG2.txt", mG2);
//		createWekaData("src/test/resources/wekaDataG3.txt", mG3);	
		createData("src/test/resources/wekaDataG1.txt", mG1);
		createData("src/test/resources/wekaDataG2.txt", mG2);
		createData("src/test/resources/wekaDataG3.txt", mG3);
		
		g1Vectors = readLines("src/test/resources/wekaDataG1.txt");
		System.out.println("G1 Vector");
		print(g1Vectors);
		
		g2Vectors = readLines("src/test/resources/wekaDataG2.txt");
		System.out.println("G2 Vector");
		print(g2Vectors);
		
		g3Vectors = readLines("src/test/resources/wekaDataG3.txt");
		System.out.println("G3 Vector");
		print(g3Vectors);

		
		g1VectorsNorm = normalizeVectors(g1Vectors);
		System.out.println("G1 Vector Norm");
		print(g1VectorsNorm);
		
		g2VectorsNorm = normalizeVectors(g2Vectors);
		System.out.println("G2 Vector Norm");
		print(g2VectorsNorm);
		
		g3VectorsNorm = normalizeVectors(g3Vectors);
		System.out.println("G3 Vector Norm");
		print(g3VectorsNorm);
		
		List<List<Double>> myData = new ArrayList<List<Double>>();
		myData.addAll(g1VectorsNorm);
		myData.addAll(g2VectorsNorm);
		myData.addAll(g3VectorsNorm);
		
		Util util = new Util();
		util.Add(myData);
		
		util.clustering(3, 20, null);
		
		System.out.println("My Result");
		util.printResults();
		
		
		for(int i = 0; i < util.getCentroids().length; i++){
			
			List<Double> cen = arrayToList(util.getCentroids()[i]);
			centroidVectors.add(cen);
			
		}
		centroidVectorsNorm = normalizeVectors(centroidVectors);
		
		
		System.out.println("My Centroid");
		for(int i = 0; i < centroidVectorsNorm.size(); i++){
			
			System.out.println(centroidVectorsNorm.get(i).toString());
			
		}
		
		
//		centroidVectors = readLines("src/test/resources/centroid.txt");
//		centroidVectorsNorm = normalizeVectors(centroidVectors);
//		
//			
		System.out.println("\n============================================\n");
		
		accuracyG1(g1VectorsNorm, "Group 1");
		accuracyG2(g2VectorsNorm, "Group 2");
		accuracyG3(g3VectorsNorm, "Group 3");
		
		int numberOfImages = g1Vectors.size() + g2Vectors.size() + g3Vectors.size();
		
		System.out.println("Number of images correctly gruped: " + numberOfImagesCorrectlyGrouped);
		
		System.out.println("Accuracy: " + 100 * numberOfImagesCorrectlyGrouped / numberOfImages + " %");
		
		
	}
	
	
	private static void accuracyG1(List<List<Double>> data, String tag){
		
		System.out.println(tag + " has " + data.size() + " memebers");
		
		for(List<Double> tempG1 : data){

			//distance between
			double disG1 = distance(tempG1, centroidVectorsNorm.get(0));
			double disG2 = distance(tempG1, centroidVectorsNorm.get(1));
			double disG3 = distance(tempG1, centroidVectorsNorm.get(2));
			
			System.out.print(tag + ": " + tempG1);
			if((disG1 < disG2) && (disG1 < disG3)){
				
				System.out.print(" Correct\n");
				numberOfImagesCorrectlyGrouped++;
				
			}else{
				System.out.println("");
			}
		}
		
	}
	
	
	private static void accuracyG2(List<List<Double>> data, String tag){
		
		System.out.println(tag + " has " + data.size() + " memebers");
		
		for(List<Double> tempG2 : data){

			//distance between
			double disG1 = distance(tempG2, centroidVectorsNorm.get(0));
			double disG2 = distance(tempG2, centroidVectorsNorm.get(1));
			double disG3 = distance(tempG2, centroidVectorsNorm.get(2));
			
			System.out.print(tag + ": " + tempG2);
			if((disG2 < disG1) && (disG2 < disG3)){
				
				System.out.print(" Correct\n");
				numberOfImagesCorrectlyGrouped++;
				
			}else{
				System.out.println("");
			}
		}
		
	}
	
	
	private static void accuracyG3(List<List<Double>> data, String tag){
		
		System.out.println(tag + " has " + data.size() + " memebers");
		
		for(List<Double> tempG3 : data){

			//distance between
			double disG1 = distance(tempG3, centroidVectorsNorm.get(0));
			double disG2 = distance(tempG3, centroidVectorsNorm.get(1));
			double disG3 = distance(tempG3, centroidVectorsNorm.get(2));
			
			System.out.print(tag + ": " + tempG3);
			if((disG2 < disG1) && (disG1 < disG3)){
				
				System.out.print(" Correct\n");
				numberOfImagesCorrectlyGrouped++;
				
			}else{
				System.out.println("");
			}
		}
		
	}
	

	
	private static List<List<Double>> normalizeVectors(List<List<Double>> vectors){
		
		List<List<Double>> returnVectors = new ArrayList<List<Double>>();
		
		for(List<Double> temp : vectors){
			
			List<Double> norm = normalize(temp);
			
			returnVectors.add(norm);
			
		}
		
		
		return returnVectors;
	}
	
	private static List<Double> normalize(List<Double> vector) {
		
		List<Double> returnVector = vector;
		
		double sum = 0;
		double average = 0.0;
		double temp = 0.0;
		
		for(int i = 0; i < vector.size(); i++){
			sum = sum + vector.get(i);
			temp = temp + vector.get(i) * vector.get(i);
		}
		
		average = sum / vector.size();
		
		for(int i = 0; i < vector.size(); i++){
//			System.out.println(i);
//			returnVector.set(i, Math.abs(vector.get(i) - average) / Math.sqrt(temp));
			returnVector.set(i, vector.get(i) / Math.sqrt(temp));
		}
		
		return returnVector;
	}
	
	
	private static double distance(List<Double> instance1, List<Double> instance2) {
	    double dist = 0.0;

	    for (int i = 0; i < instance1.size(); i++) {
	        double x = instance1.get(i);
	        double y = instance2.get(i);

	        if (Double.isNaN(x) || Double.isNaN(y)) {
	            continue; // Mark missing attributes ('?') as NaN.
	        }

	        dist += (x-y)*(x-y);
	    }

	    return Math.sqrt(dist);
	}
	
	
	private static List<List<Double>> readLines(String path) throws IOException{
		
		String line;
		
		List<List<Double>> returnLines = new ArrayList<List<Double>>();
		
		try(
				InputStream fis = new FileInputStream(path);
				InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
				BufferedReader br = new BufferedReader(isr);
		){
			while((line = br.readLine()) != null){
				//2, 0, 4, 0, 0, 0, 2, 0, 0
				String[] temp = line.split(",");
				
				List<Double> vector = new ArrayList<Double>();
				vector = arrayToList(temp);
				
				returnLines.add(vector);
			}
		}
		
		return returnLines;
	}
	
	
	private static List<Double> arrayToList(String[] array){
		
		List<Double> returnList = new ArrayList<Double>();
		
		for(int i = 0; i < array.length; i++){
			
			returnList.add(Double.parseDouble(array[i]));
			
		}
		
		return returnList;
	}
	
	
	private static List<Double> arrayToList(double[] array){
		
		List<Double> returnList = new ArrayList<Double>();
		
		for(int i = 0; i < array.length; i++){
			
			returnList.add(array[i]);
			
		}
		
		return returnList;
		
		
	}
	
	
	private static void createWekaData(String path, Matrix m) throws IOException{
		
//		String path = "src/test/resources/wekaData.txt";
		deleteFile(path);
		m.writeLine(path, "@relation data");
		m.writeLine(path, "");
		m.writeLine(path, "@attribute PolygonFive;Blue numeric");
		
		m.writeLine(path, "@attribute PolygonSix;Red numeric");
		m.writeLine(path, "@attribute regionPoly numeric");
		
		m.writeLine(path, "@attribute regionRect numeric");
		m.writeLine(path, "@attribute Rectangle;Crimson numeric");
		m.writeLine(path, "@attribute Trapezoidal;Green numeric");
		m.writeLine(path, "@attribute Ellipse;DarkBlue numeric");
		m.writeLine(path, "@attribute regionQuar numeric");
		m.writeLine(path, "@attribute QuarterMiss;Lavender numeric");
		m.writeLine(path, "");
		m.writeLine(path, "@data");
		
		for(List<Integer> v : m.getCooccurrance()){			
			m.writeLine(path, v.toString().replace("[", "").replaceAll("]", ""));			
		}
		
	}
	
	
	private static void createData(String path, Matrix m) throws IOException{
		deleteFile(path);
		
		for(List<Integer> v : m.getCooccurrance()){			
			m.writeLine(path, v.toString().replace("[", "").replaceAll("]", ""));			
		}
		
	}
	
	private static void deleteFile(String path){
		
		try{
			File file = new File(path);
			if(file.exists()){
				file.delete();
				System.out.println("\nFile " + path + " is deleted \n");
			}else{
				System.out.println("\nFile " + path + " cannot find to delete \n");
			}
		}catch (Exception e){
			System.out.println("Cannot delete file " + path);
		}
	}
	
	private static void print(List<List<Double>> t){
		
		System.out.println("There are " + t.size() + "vectors");
		
		for(List<Double> temp : t) {
			
			System.out.println(temp.toString());
			
		}
		
		System.out.println();
	}

}

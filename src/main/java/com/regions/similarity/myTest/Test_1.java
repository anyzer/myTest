package com.regions.similarity.myTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import weka.clusterers.SimpleKMeans;
import weka.core.Instance;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

public class Test_1 {

	public static HigherOrderBoVW myTwoDStringG1 = new HigherOrderBoVW();
	public static HigherOrderBoVW myTwoDStringG2 = new HigherOrderBoVW();
	public static HigherOrderBoVW myTwoDStringG3 = new HigherOrderBoVW();

	public static List<String> poly = new ArrayList<String>();
	public static List<String> rect = new ArrayList<String>();
	public static List<String> quar = new ArrayList<String>();

	public static void main(String[] args) throws Exception {

		prepare2DString();
		// upto here: replaced 2D String representation is ready, replaced
		// region is ready

		Matrix m = new Matrix(myTwoDStringG1.getR());
		m.replaceRegions("regionPoly", poly);
		m.replaceRegions("regionRect", rect);
		m.replaceRegions("regionQuar", quar);
		
		System.out.println("\nFinal Regions: \n");
		for(String reg : m.getRegionsReplaced()){
			System.out.print(reg + "-");
		}

		System.out.println("\n===================================================");
		System.out.println("\n");
		
		m.createCooccurranceMatrix(myTwoDStringG1.getaReplaced());
		System.out.println("-----------------------------------------------------");
		m.createCooccurranceMatrix(myTwoDStringG2.getaReplaced());
		System.out.println("-----------------------------------------------------");
		m.createCooccurranceMatrix(myTwoDStringG3.getaReplaced());
		System.out.println("-----------------------------------------------------");
		
		System.out.println(m.getCooccurrance().size());
		System.out.println(m.getCooccurrance().get(0).size());
		
		createWekaData(m);
		
		BufferedReader datafile = readDataFile("src/test/resources/wekaData.txt");
		
		Instances dataRow = new Instances(datafile);
		
		Normalize norm = new Normalize();
		norm.setInputFormat(dataRow);
		Instances data = Filter.useFilter(dataRow, norm);
		
//		data.setClassIndex(data.numAttributes() - 1);
		
		SimpleKMeans kmeans = new SimpleKMeans();
		kmeans.setSeed(6);
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setNumClusters(3);
		
		kmeans.buildClusterer(data);
		
		int[] assignments = kmeans.getAssignments();
		 
		int i=0;
		for(int clusterNum : assignments) {
		    System.out.printf("Instance %d -> Cluster %d \n", i, clusterNum);
		    i++;
		}
		
		System.out.println("-------");
		
		Instances centroids = kmeans.getClusterCentroids();
		
		centroids.setClassIndex(centroids.numAttributes() - 1);

		System.out.println("Centroids " + centroids);

		//use the centorid as input for evaluate class
		
//		Instance g1 = centroids.instance(0);
//		System.out.println(g1);
//				
//		List<Instance> group1 = new ArrayList<Instance>();
//		for(int l = 0; l < 40; l++){
//			
//			Instance temp = new Instance(1);
//			temp.setValue("vector", m.getCooccurrance().get(i));
//			group1.add();
//			
//		}
		
		System.out.println("===");
		String attValue = "";
		double[] temp = dataRow.attributeToDoubleArray(0);
		for(int l = 0; l < temp.length; l++){
			attValue = attValue + temp[l] + ",";
		}
		System.out.println(temp.length);
		System.out.println(attValue);
		System.out.println("===");
			
		
		
		
		
		
		
	}

//==========================================================================================	
	
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}
	
	public static Evaluation classify(Classifier model,
			Instances trainingSet, Instances testingSet) throws Exception {
		Evaluation evaluation = new Evaluation(trainingSet);
 
		model.buildClassifier(trainingSet);
		evaluation.evaluateModel(model, testingSet);
 
		return evaluation;
	}
	
	public static double calculateAccuracy(FastVector predictions) {
		double correct = 0;
 
		for (int i = 0; i < predictions.size(); i++) {
			NominalPrediction np = (NominalPrediction) predictions.elementAt(i);
			if (np.predicted() == np.actual()) {
				correct++;
			}
		}
 
		return 100 * correct / predictions.size();
	}
	
	public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
		Instances[][] split = new Instances[2][numberOfFolds];
 
		for (int i = 0; i < numberOfFolds; i++) {
			split[0][i] = data.trainCV(numberOfFolds, i);
			split[1][i] = data.testCV(numberOfFolds, i);
		}
 
		return split;
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
	
	
	private static void createWekaData(Matrix m) throws IOException{
		String path = "src/test/resources/wekaData.txt";
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

	//add this similarity between regions
	private static void addSimilarity() {
//		poly.add("PolygonSix;Red");// 1
		poly.add("PolygonEight;Chartreuse");// 0.778
		poly.add("default;Chocolate");// 0.774

		rect.add("Triangle;Coral");// 0.75
//		rect.add("Rectangle;Crimson");// 0.9285
		rect.add("Moon;DarkGray");// 0.7894

		quar.add("Halfcircle;Pink");// 0.9
		quar.add("Quarter;Goldenrod");// 0.8965
//		quar.add("QuarterMiss;Lavender");// 1
	}

	private static void prepare2DString() throws IOException {

		addSimilarity();

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

	}

}

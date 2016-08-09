package com.base.classes;

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
import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import weka.core.FastVector;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

public class Accuracy extends Instance  {

	
	public static void main(String[] args){
		
		BufferedReader datafile = readDataFile("src/test/resources/wekaData.txt");	
		BufferedReader centroid = readDataFile("src/test/resources/wekaDataCentroid.txt");
//		EuclideanDistance euc = new EuclideanDistance();
		
		boolean g1Correct = false;
		int g1CorrectNumber = 0;
		
		try {
			
			Instances dataRow = new Instances(datafile);
			Instances centroids = new Instances(centroid);
			
			for(int i = 0; i < 38; i++){
				
				Instance t = dataRow.instance(i);
				Instance s = centroids.instance(0);
				EuclideanDistance euc = new EuclideanDistance();
				
				double g0 = euc.distance(dataRow.instance(i), centroids.instance(0));
				double g1 = euc.distance(dataRow.instance(i), centroids.instance(1));
				double g2 = euc.distance(dataRow.instance(i), centroids.instance(2));
				
				if((g0 < g1) && (g0 < g2)){
					g1Correct = true;
					g1CorrectNumber = g1CorrectNumber + 1;
				}else{
					g1Correct = false;
				}
				
				System.out.print("Distance to centroids: " + g0 + "-" + g1 + "-" + g2 + " => " + g1Correct);
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}
}

///************************************************************************************************************************
//		Author           : SGWS JDA Team 
//		Last Modified by : Nivedha Ravichandran
//		Last Modified on : 13-Feb-2020
//		Summary 		 : SQL Validations for SS Classification Rejection scenarios
//
//************************************************************************************************************************/

package DriverScript;

import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import Functions.DififoReportSetup;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Functions.DataBase;
import Functions.DififoReportSetup;
import Functions.ExcelFile;

public class Ssclassification  extends DififoReportSetup{
	
	String ItemNumber;
	String LocationNum;
	String testScenarioFilePath;
	String testCaseFileName;
	String testdatasheet;
	String DLYdata;
	String inputDatafromDBFileName;
	String skuData;
	String PIMdata;
	String Inputsheet;
	
	ExcelFile exfile = new ExcelFile();
	
	
	
	public String fetchItemNumber(String filePa, String fileNa,String SheetNa,int row,int col) throws IOException{
    	ItemNumber = exfile.readExcel(filePa, fileNa, SheetNa, row, 0);
    	return ItemNumber;
            }
	public String fetchNewItemNumber(String filePa, String fileNa,String SheetNa,int row,int col) throws IOException{
    	ItemNumber = exfile.readExcel(filePa, fileNa, SheetNa, row, 2);
    	return ItemNumber;
            }
	public String fetchLocationNumber(String filePa, String fileNa,String SheetNa,int row,int col) throws IOException{
    	LocationNum = exfile.readExcel(filePa, fileNa, SheetNa, row, 1);
    	return LocationNum;
            }
	
	@BeforeTest
	public void excelFileClear() throws IOException, InterruptedException
	{
		try {
		InputStream envPropInput = new FileInputStream("./Environment\\Environment.properties");
		Properties envProp = new Properties();
		envProp.load(envPropInput);
				
		testScenarioFilePath = envProp.getProperty("testScenarioFilePath");
		inputDatafromDBFileName = envProp.getProperty("inputDatafromDBFileName");
		testCaseFileName = envProp.getProperty("testCaseFileName");
		PIMdata = envProp.getProperty("PIMdata");
		DLYdata = envProp.getProperty("DLYdata");
		skuData = envProp.getProperty("SKUData");
		Inputsheet = envProp.getProperty("testdatasheet");
		System.out.println("Excel clear done");
		report.log("Clearing the Last run results from DB.xlsx - all sheets");

		for(int i=0;i<=15;i++)
		{
		exfile.ClearCell(testScenarioFilePath, inputDatafromDBFileName, PIMdata, i);
		exfile.ClearCell(testScenarioFilePath, inputDatafromDBFileName, DLYdata, i);
		exfile.ClearCell(testScenarioFilePath, inputDatafromDBFileName, skuData, i);
		exfile.ClearCell(testScenarioFilePath, testCaseFileName, Inputsheet, i);
		}
		}
		catch(Exception e) {
			return;
		}
	}	
	
	@Test(priority=0)
		public void testData() throws IOException, SQLException {
			InputStream envPropInput = new FileInputStream("./Environment\\Environment.properties");
			Properties envProp = new Properties();
			envProp.load(envPropInput);
			
			testScenarioFilePath = envProp.getProperty("testScenarioFilePath");
			testCaseFileName = envProp.getProperty("testCaseFileName");
			testdatasheet = envProp.getProperty("testdatasheet");
			System.out.println("hello");
			DataBase db= new DataBase();
			InputStream queryPropInput = new FileInputStream("./DB Query\\Query1.properties");
			Properties queryProp = new Properties();
			queryProp.load(queryPropInput);
			String Query = queryProp.getProperty("JDAtestinput");
			String Query1 = queryProp.getProperty("JDAtestinput1");
			db.dbJDATestInput(Query + Query1);
		}
	
	
	@Test(priority=1)
	public void dbPIMSupersession() throws IOException, SQLException, InterruptedException 
	{
		
		InputStream envPropInput = new FileInputStream("./Environment\\Environment.properties");
		Properties envProp = new Properties();
		envProp.load(envPropInput);
		
		testScenarioFilePath = envProp.getProperty("testScenarioFilePath");
		testCaseFileName = envProp.getProperty("testCaseFileName");
		testdatasheet = envProp.getProperty("testdatasheet");
		
		int rowMax = exfile.getTotalRowColumn(testScenarioFilePath,testCaseFileName,testdatasheet);
		
		report.log("No of Ultimate Parent provided by the user: "+ rowMax );
		
		for (int i=1;i<=rowMax;i++)
		{	
		String Item = fetchItemNumber(testScenarioFilePath,testCaseFileName,testdatasheet,i,0);
		
		DataBase db= new DataBase();
		InputStream queryPropInput = new FileInputStream("./DB Query\\Query1.properties");
		Properties queryProp = new Properties();
		queryProp.load(queryPropInput);
		String Query = queryProp.getProperty("PIMSupersession");
		db.dbPIMSupersessionConn(Query + Item +"'");
		}
	}
	
	@Test(priority=2)
	public void dbDLYSupersession() throws IOException, SQLException, InterruptedException {
		
		InputStream envPropInput = new FileInputStream("./Environment\\Environment.properties");
		Properties envProp = new Properties();
		envProp.load(envPropInput);
		
		testScenarioFilePath = envProp.getProperty("testScenarioFilePath");
		testCaseFileName = envProp.getProperty("testCaseFileName");
		testdatasheet = envProp.getProperty("testdatasheet");
		
		int rowMax = exfile.getTotalRowColumn(testScenarioFilePath,testCaseFileName,testdatasheet);
		System.out.println("No of Ultimate Parent provided by the user: "+ rowMax );

		report.log("No of Ultimate Parent provided by the user: "+ rowMax );

		for (int i=1;i<=rowMax;i++)
		{	
		String Item = fetchItemNumber(testScenarioFilePath,testCaseFileName,testdatasheet,i,0);
		
		DataBase db= new DataBase();
		InputStream queryPropInput = new FileInputStream("./DB Query\\Query1.properties");
		Properties queryProp = new Properties();
		queryProp.load(queryPropInput);
		String Query = queryProp.getProperty("DLYSupersession");
		db.dbSupersessionDLYConn(Query + Item +"'");
		}
	}
		
	@Test(priority=3)
		public void dbSKU() throws IOException, SQLException, InterruptedException 
		{
	        
			InputStream envPropInput = new FileInputStream("./Environment\\Environment.properties");
			Properties envProp = new Properties();
			envProp.load(envPropInput);
					
			testScenarioFilePath = envProp.getProperty("testScenarioFilePath");
			inputDatafromDBFileName = envProp.getProperty("inputDatafromDBFileName");
			DLYdata = envProp.getProperty("DLYdata");
			
			
			int rowMax1 = exfile.getTotalRowColumn(testScenarioFilePath, inputDatafromDBFileName, DLYdata);
			System.out.println("\"No of records in SKU table for given ultimate Parent: "+ rowMax1 );
			report.log("No of records in DLY Supersession table for given ultimate Parent: "+ rowMax1 );
			report.log("Validating whether the Sku/Sourcing is Available or not");

			for (int i=1;i<=rowMax1;i++)
			{	
			String OldItem = fetchItemNumber(testScenarioFilePath,inputDatafromDBFileName,DLYdata,i,0);
			String NewItem = fetchNewItemNumber(testScenarioFilePath,inputDatafromDBFileName,DLYdata,i,2);
			String Location=fetchLocationNumber(testScenarioFilePath,inputDatafromDBFileName,DLYdata,i,1);
			DataBase db= new DataBase();
			InputStream queryPropInput = new FileInputStream("./DB Query\\Query1.properties");
			Properties queryProp = new Properties();
			queryProp.load(queryPropInput);
			String Query = queryProp.getProperty("SKU");
			report.log("Currently checking for record "+i);
			db.oldSkuCheck(Query + OldItem +"'"+"and dest='" +Location +"'",OldItem,i);
			db.newSkuCheck(Query + NewItem +"'"+"and dest='" +Location +"'",NewItem,i);	
			db.cellMerge(i);
			}	


   }
	@Test(priority=4)
	public void dbSKUReject() throws IOException, SQLException, InterruptedException 
	{        
		InputStream envPropInput = new FileInputStream("./Environment\\Environment.properties");
		Properties envProp = new Properties();
		envProp.load(envPropInput);
				
		testScenarioFilePath = envProp.getProperty("testScenarioFilePath");
		inputDatafromDBFileName = envProp.getProperty("inputDatafromDBFileName");
		DLYdata = envProp.getProperty("DLYdata");
		
		int rowMax1 = exfile.getTotalRowColumn(testScenarioFilePath, inputDatafromDBFileName, DLYdata);
		report.log("No of records in  Supersession table for the given ultimate Parent: "+ rowMax1 );

		for (int i=1;i<=rowMax1;i++)
		{	
		String FromItem = fetchItemNumber(testScenarioFilePath,inputDatafromDBFileName,DLYdata,i,0);
		String ToItem = fetchNewItemNumber(testScenarioFilePath,inputDatafromDBFileName,DLYdata,i,2);
		String Location=fetchLocationNumber(testScenarioFilePath,inputDatafromDBFileName,DLYdata,i,1);
		DataBase db= new DataBase();
		InputStream queryPropInput = new FileInputStream("./DB Query\\Query1.properties");
		Properties queryProp = new Properties();
		queryProp.load(queryPropInput);
		String Query = queryProp.getProperty("SKUREJECT");
		report.log("Currently Executing for record "+i);
		db.dbSkuRejection(Query + FromItem +"'"+"and toitem = '"+ ToItem +"' and loc='" +Location +"' group by fromitem,toitem,loc,reject_reason");
		
		}	
		}
	
	@Test(priority=5)
	public void dbCompare() throws IOException, SQLException, InterruptedException 
	{        
		InputStream envPropInput = new FileInputStream("./Environment\\Environment.properties");
		Properties envProp = new Properties();
		envProp.load(envPropInput);
				
		testScenarioFilePath = envProp.getProperty("testScenarioFilePath");
		inputDatafromDBFileName = envProp.getProperty("inputDatafromDBFileName");
		skuData = envProp.getProperty("SKUData");
		DLYdata = envProp.getProperty("DLYdata");

		
		int rowMaxExt = exfile.getTotalRowColumn(testScenarioFilePath, inputDatafromDBFileName, DLYdata);
		int rowMaxSku = exfile.getTotalRowColumn(testScenarioFilePath, inputDatafromDBFileName, skuData);
		report.log("The number of data in DLY supersession: "+ rowMaxExt);
		report.log("The number of data in SKU Rejections: "+ rowMaxSku);
		


		for (int i=1;i<=rowMaxExt;i++)
		{
			for (int j=1;j<=rowMaxSku;j++)
			{
			
			DataBase db= new DataBase();
			db.rejectioncompare(i,j);
		}
			}
		
		for (int i=1;i<=rowMaxExt;i++)
		{
			for (int j=1;j<=rowMaxSku;j++)
			{
			
			DataBase db= new DataBase();
			db.invalidData(i,j);
		}
		}
		report.log("The Rejections data in WIP_SAP_SUPERSESSION_NEW_REJ is validated");
		report.log("Please refer the attached results in Db.xlsx excel in Sheet - SKU_Rejection");
	}
	}
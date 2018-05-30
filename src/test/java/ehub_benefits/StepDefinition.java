package ehub_benefits;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
//import junit.framework.Assert; old and deprecated
import org.junit.Assert;

import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.remote.*;
//import org.testng.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//import cts.mars.*;

//import org.sikuli.script.*;



public class StepDefinition {
	String[][] returnTD = new String[1000][10];

	@Given("^Test Data is in RECEIVED state in CS90_CONTRACT_MASTER table$")
	public void given_data_is_in_received_state() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new RuntimeException("When is failing");
		/*
	    try {
			Assert.fail("dummy failure");
		}
		catch (AssertionError e) {
			Thread.sleep(1);
			System.out.println("Assert Failed in @When");
		} */
		
	}

	@When("^Java Batch Engine processes provided Contract Codes$")
	public void when_Batch_Engine_processes_Contracts(Map<String, String> SelData) throws Throwable {
		//System.out.println("??????????? here ???????????");
		WebDriver wd = selenium_init.SelDriverInit();
		
		String sheet = SelData.entrySet().stream().findFirst().get().getKey();
		String TCnm  = SelData.entrySet().stream().findFirst().get().getValue();
		
		//System.out.println(" TC Name and Sheet " + sheet + TCnm);
		//Thread.sleep(10000000);
		
		returnTD = ExcelOps.FetchSelTD(sheet, TCnm);
		System.out.println("FetchSelTD is Success");
		
		for(int i=0; i<returnTD.length; i++) { //data table for loop
			String cntrct = returnTD[i][0];
			if (cntrct == null)	
				break;
			else	{
				wd.get("http://vaathmr868:9092/mapengine"); // Open URL
				System.out.println("Opened URL & Passing Contract Code:" + cntrct);
				wd.manage().window().maximize();
			
				WebElement ContractCdTxtBox = wd.findElement(By.xpath("//*[@id=\"mbr-page-wrapper\"]/div[2]/div/table/tbody/tr[1]/td[2]/textarea"));   //xpath
				ContractCdTxtBox.sendKeys(cntrct);
				Thread.sleep(2000);
				WebElement SendBtn = wd.findElement(By.xpath("//*[@id='submit']"));   //xpath: //*[@id="submit"]
				SendBtn.click();
				Thread.sleep(10000);
				
				WebElement message = wd.findElement(By.xpath("//*[@id='msg']")); //path: //*[@id="msg"]
				String msg = message.getText();
				System.out.println(msg);
			}
				
		} // end for loop
	  
		//System.out.println("Waiting 5 sec...");
		//Thread.sleep(5000);
		//System.out.println("Waited 5 sec before exit");
		System.out.println("Finished WHEN section");
		wd.close();  // Close webdriver & browser
	}

	
	
	@Then("^Validate columns of RAW Input table CS90_CONTRACT_MASTER are moved to CS90_EHUB_PROD Table$")
	public void then_validate_ETL_in_database_layer(Map<String, String> sqlVsSqlList) throws Throwable {
		//sqlVsSqlList.forEach((k,v)->System.out.println("To_Run_Column : " + k + " SQL_Dump_File_Name : " + v));
		//Database Section ----------------
		Connection DbConn = database.db_conn();
		ResultSet SRCrs = null; ResultSet TRGrs = null;
		//Map<String, String> SQLMap;
		//SQLMap = ExcelOps.xlsFetchSQLpair();
		
		for(int i=0; i<returnTD.length; i++) { //data table for loop
			String Tdata  = returnTD[i][0];
			String SRCsql = returnTD[i][1];
			String TRGsql = returnTD[i][2];
			if (SRCsql == null || TRGsql == null)	
				break;
			else	{
				try { //SQL1
					SRCrs = database.sql_exec(DbConn, SRCsql, Tdata);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try { //SQL 2
					TRGrs = database.sql_exec(DbConn, TRGsql, Tdata);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
				
		} // end for loop
		

		SparkOps.SparkComp(SRCrs, TRGrs);
		
		if (!SRCrs.equals(TRGrs)) {
			//Assert.fail(); // this works with JUPITER Reports
			throw new RuntimeException("The two ResultSets contains different Values!");
		}
		

		

		DbConn.close();
	}



}

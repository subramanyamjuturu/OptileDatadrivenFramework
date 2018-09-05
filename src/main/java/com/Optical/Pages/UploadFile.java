package com.Optical.Pages;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.Optical.Actions.Action;
import com.Optical.Results.ResultsClass;
import com.Optical.Scripts.ExcelOperations;
import com.Optical.Scripts.ScriptExecuter;
import com.Optical.TestNg.Testng;

public class UploadFile extends Testng{
	
	static String ObjectProprty;
	static String ObjectProprtyValue;


	static ResultsClass Report = new ResultsClass();
	static Action actions = new Action();
	static ExcelOperations fileoperations = new ExcelOperations();
	LoginPage login= new LoginPage();
	
	public void uploadFileFunctionality() throws Exception
	{
		// getting object properties and driver details from Data sheet and storing in Collections
		Map<String,String> data =fileoperations.readData(CurrentFileName, "OpticalTaskLogin", 1);
		Map<String,String> Logindata =fileoperations.readData(CurrentFileName, "LoginPage", 1);
		
		String Username= Logindata.get("Username");
		String Password= Logindata.get("Password");
	
		//This is for Launching Saloodo site and inside this method Detailed Report code is available.
		actions.browserLaunch(Logindata.get("URL_Data"), driver, TempResultFile, "Launching Browser");


		String source =actions.pageSource(driver);

		if(source.contains("xml:lang=\"de\""))
		{
			actions.Click("GermanSignInButton", "GermanSignInButton", "SignIn button", driver, TempResultFile, "Clicking on Sign In");

		}
		else if(source.contains("xml:lang=\"en\"")){

			actions.Click("EnglishSignInButton", "EnglishSignInButton", "SignIn button", driver, TempResultFile, "Clicking on Sign In");

		}

		login.loginUser(Username,Password);

		login.VerifyUserLogged();

		uploadFile();

		actions.Click("UserIcon", "UserIcon", "UserIcon", driver, TempResultFile, "Clicking on User Icon");

		actions.Click("Logout", "Logout", "Logout Button", driver, TempResultFile, "Clicking on Logout Button");


	}

	String FilePath = strAbsolutePath+"\\FileUpload\\DummyFile.txt";
	String FileName [] =FilePath.split("\\\\FileUpload\\\\");

	public void uploadFile() throws Exception
	{
		actions.Click("Home", "Home", "Home", driver, TempResultFile, "Clicking on Home");
		actions.Click("UploadButton", "UploadButton", "UploadButton", driver, TempResultFile, "Clicking on upload button");
		actions.Click("UploadFile", "UploadFile", "UploadFile", driver, TempResultFile, "Clicking on upload file");

		StringSelection ss = new StringSelection(FilePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

		Robot robot = new Robot();

		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		Thread.sleep(1000);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(1000);
		robot.keyRelease(KeyEvent.VK_ENTER);

		actions.Click("DropBoxInPopUp", "DropBoxInPopUp", "DropBoxInPopUp", driver, TempResultFile, "Clicking on DropBox in pop up");
		actions.Click("UploadButtonInPopUp", "UploadButtonInPopUp", "UploadButtonInPopUp", driver, TempResultFile, "Clicking on upload file");
		actions.Click("ViewDetails", "ViewDetails", "ViewDetails", driver, TempResultFile, "Clicking on ViewDetails");
		actions.Click("Done", "Done", "Done", driver, TempResultFile, "Clicking on Done");

		Thread.sleep(6000);
		ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("FileList");

		List<WebElement> ele = driver.findElements(By.xpath(ObjectProprtyValue));

		for(int i=0;i<ele.size();i++)
		{
			System.out.println(ele.get(i).getText());
			System.out.println(FileName[1]);
			if(ele.get(i).getText().equalsIgnoreCase(FileName[1]))
			{
				Report.ResultWrite(TempResultFile, "verifying File uploaded sucessfully",FileName[1],"verifying",ele.get(i).getText(),"Pass",driver,true);
				break;
			}
			else if(i==ele.size())
			{
				Report.ResultWrite(TempResultFile, "verifying File uploaded sucessfully",FileName[1],"verifying",ele.get(i).getText(),"Fail",driver,true);
			}
		}

	}



}

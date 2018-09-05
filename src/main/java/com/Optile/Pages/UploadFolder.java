package com.Optile.Pages;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.Optile.Actions.Action;
import com.Optile.Results.ResultsClass;
import com.Optile.Scripts.ExcelOperations;
import com.Optile.Scripts.ScriptExecuter;
import com.Optile.TestNg.Testng;

public class UploadFolder extends Testng {
	
	
	static String ObjectProprty;
	static String ObjectProprtyValue;


	static ResultsClass Report = new ResultsClass();
	static Action actions = new Action();
	static ExcelOperations fileoperations = new ExcelOperations();
	LoginPage login= new LoginPage();
	
	
	public String createFolderFunctionality() throws Exception
	{
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

		String FolderName = createFolder();
		
		return FolderName;


	}
	
	public String createFolder() throws Exception
	{
		
		Random rand = new Random();
		double  n = rand.nextInt(50)*1.13;

		String FolderName ="DummyFile"+n;
		String Rename ="RenameFile"+n;
		
		actions.Click("DropBoxHomeIcon", "DropBoxHomeIcon", "DropBoxHomeIcon", driver, TempResultFile, "Clicking on DropBox Home Icon");

		actions.Click("Files", "Files", "Files", driver, TempResultFile, "Clicking on Files");
		actions.Click("NewFolder", "NewFolder", "NewFolder", driver, TempResultFile, "Clicking on NewFolder");


		StringSelection ss = new StringSelection(FolderName);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

		Thread.sleep(1000);

		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		Thread.sleep(1000);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(1000);
		robot.keyRelease(KeyEvent.VK_ENTER);

		Thread.sleep(5000);

		ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("UploadedFolderName");


		List<WebElement> ele = driver.findElements(By.xpath(ObjectProprtyValue));

		for(int i=0;i<ele.size();i++)
		{
			System.out.println(ele.get(i).getText());
			System.out.println(FolderName);
			if(ele.get(i).getText().equalsIgnoreCase(FolderName))
			{
				Report.ResultWrite(TempResultFile, "verifying File uploaded sucessfully",FolderName,"verifying",ele.get(i).getText(),"Pass",driver,true);
				break;
			}
			else if(i==ele.size())
			{
				Report.ResultWrite(TempResultFile, "verifying File uploaded sucessfully",FolderName,"verifying",ele.get(i).getText(),"Fail",driver,true);
			}
		}
		
		return FolderName;

	}
	
	public void createDeleteFolderFunctionality() throws Exception
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


		deleteCreatedFolderFunctionality();


		actions.Click("UserIcon", "UserIcon", "UserIcon", driver, TempResultFile, "Clicking on User Icon");

		actions.Click("Logout", "Logout", "Logout Button", driver, TempResultFile, "Clicking on Logout Button");


	}
	public void deleteCreatedFolderFunctionality() throws Exception
	{

		String FolderName =createFolder();

		actions.Click("MyFiles", "MyFiles", "MyFiles", driver, TempResultFile, "Clicking on MyFiles");

		ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("UploadedFolderName");

		List<WebElement> ele = driver.findElements(By.xpath(ObjectProprtyValue));

		for(int i=0;i<ele.size();i++)
		{
			System.out.println(ele.get(i).getText());
			System.out.println(FolderName);

			if(ele.get(i).getText().equalsIgnoreCase(FolderName))
			{
				Report.ResultWrite(TempResultFile, "verifying File uploaded sucessfully",FolderName,"verifying",ele.get(i).getText(),"Pass",driver,true);

				actions.Click("FileOptions", "FileOptions", "FileOptions", driver, TempResultFile, "Clicking on File Options");
				actions.Click("Delete", "Delete", "Delete", driver, TempResultFile, "Clicking on File Delete");
				actions.Click("PopUpDeleteConformation", "PopUpDeleteConformation", "PopUpDeleteConformation", driver, TempResultFile, "Clicking on Popup delete Conformation");

				Thread.sleep(3000);

				break;
			}
			else if(i==ele.size())
			{
				Report.ResultWrite(TempResultFile, "verifying File uploaded sucessfully",FolderName,"verifying",ele.get(i).getText(),"Fail",driver,true);
			}
		}

		actions.Click("DeletedFiles", "DeletedFiles", "DeletedFiles", driver, TempResultFile, "Clicking on DeletedFiles");

		ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("UploadedFolderName");

		ele = driver.findElements(By.xpath(ObjectProprtyValue));

		for(int i=0;i<ele.size();i++)
		{
			System.out.println(ele.get(i).getText());
			System.out.println(FolderName);
			if(ele.get(i).getText().equalsIgnoreCase(FolderName))
			{
				Report.ResultWrite(TempResultFile, "verifying uploaded folder is deleted sucessfully",FolderName,"verifying",ele.get(i).getText(),"Pass",driver,true);
				break;
			}
			else if(i==ele.size())
			{
				Report.ResultWrite(TempResultFile, "verifying uploaded folder is deleted sucessfully",FolderName,"verifying",ele.get(i).getText(),"Fail",driver,true);
			}
		}

	}
	public void renameCreatedFolderFunctionality() throws Exception
	{
		
		String FolderName =createFolderFunctionality();
		
		
		Random rand = new Random();
		double  n = rand.nextInt(50)*1.13;

		String Rename ="RenameFile"+n;
	

		actions.Click("MyFiles", "MyFiles", "MyFiles", driver, TempResultFile, "Clicking on MyFiles");


		ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("UploadedFolderName");

		List<WebElement> ele = driver.findElements(By.xpath(ObjectProprtyValue));

		for(int i=0;i<ele.size();i++)
		{
			System.out.println(ele.get(i).getText());
			System.out.println(FolderName);

			if(ele.get(i).getText().equalsIgnoreCase(FolderName))
			{
				actions.Click("FileOptions", "FileOptions", "FileOptions", driver, TempResultFile, "Clicking on File Options");

				actions.Click("Rename", "Rename", "Rename", driver, TempResultFile, "Clicking on File Rename");

				StringSelection ss = new StringSelection(Rename);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

				Thread.sleep(1000);

				Robot robot = new Robot();

				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				Thread.sleep(1000);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				Thread.sleep(1000);
				robot.keyPress(KeyEvent.VK_ENTER);
				Thread.sleep(1000);
				robot.keyRelease(KeyEvent.VK_ENTER);

				Thread.sleep(3000);
				break;
			}
			else if(i==ele.size())
			{
				Report.ResultWrite(TempResultFile, "verifying file is renamed  file",FolderName,"verifying",ele.get(i).getText(),"Fail",driver,true);
			}
		}


		actions.Click("MyFiles", "MyFiles", "MyFiles", driver, TempResultFile, "Clicking on MyFiles");

		ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("UploadedFolderName");

		ele = driver.findElements(By.xpath(ObjectProprtyValue));

		for(int i=0;i<ele.size();i++)
		{
			System.out.println(ele.get(i).getText());
			System.out.println(Rename);
			if(ele.get(i).getText().equalsIgnoreCase(Rename))
			{
				Report.ResultWrite(TempResultFile, "verifying uploaded folder is renamed sucessfully",Rename,"verifying",ele.get(i).getText(),"Pass",driver,true);
				break;
			}
			else if(i==ele.size())
			{
				Report.ResultWrite(TempResultFile, "verifying uploaded folder is renamed sucessfully",Rename,"verifying",ele.get(i).getText(),"Fail",driver,true);
			}
		}
		
		actions.Click("UserIcon", "UserIcon", "UserIcon", driver, TempResultFile, "Clicking on User Icon");

		actions.Click("Logout", "Logout", "Logout Button", driver, TempResultFile, "Clicking on Logout Button");


	}

}

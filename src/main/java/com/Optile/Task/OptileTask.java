package com.Optile.Task;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.Optile.Actions.Action;
import com.Optile.Pages.LoginPage;
import com.Optile.Pages.UploadFile;
import com.Optile.Pages.UploadFolder;
import com.Optile.Results.ResultsClass;
import com.Optile.Scripts.*;
import com.Optile.TestNg.Testng;

public class OptileTask extends Testng{

	static String ObjectProprty;
	static String ObjectProprtyValue;


	static ResultsClass Report = new ResultsClass();
	static Action actions = new Action();
	static ExcelOperations fileoperations = new ExcelOperations();
	LoginPage login= new LoginPage();
	UploadFile file = new UploadFile();
	UploadFolder folder = new UploadFolder();

	public OptileTask(String thread)
	{



	}

	public void OpticalTaskLogin() 
	{
		try {
			// loginf to the application
			login.Login();
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

	}


	public void uploadFileFunctionality()
	{
		try {
			// uploading folder
			file.uploadFileFunctionality();
			//for external report purpose
			Report.FinalResultWrite(TempResultFile,"uploadFileFunctionality",CurrentFileName);
			Report.InsertOverallStatus(TempResultFile,"uploadFileFunctionality",CurrentFileName);
		
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}



	}



	public void createFolderFunctionality()
	{

		try {
			// creating folder
			folder.createFolderFunctionality();
			
			actions.Click("UserIcon", "UserIcon", "UserIcon", driver, TempResultFile, "Clicking on User Icon");

			actions.Click("Logout", "Logout", "Logout Button", driver, TempResultFile, "Clicking on Logout Button");
			
			//for external report purpose
			Report.FinalResultWrite(TempResultFile,"createFolderFunctionality",CurrentFileName);
			Report.InsertOverallStatus(TempResultFile,"createFolderFunctionality",CurrentFileName);

			
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

	}

	public void createDeleteFolderFunctionality()
	{
		try {
			// creating and deleting folder
			folder.createDeleteFolderFunctionality();
			
			//for external report purpose
			Report.FinalResultWrite(TempResultFile,"createDeleteFolderFunctionality",CurrentFileName);
			Report.InsertOverallStatus(TempResultFile,"createDeleteFolderFunctionality",CurrentFileName);
		
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

	}




	public void renameCreatedFolderFunctionality() 
	{
		try {
			//creating and renaming the folder
			folder.renameCreatedFolderFunctionality();
			
			//for external report purpose
			Report.FinalResultWrite(TempResultFile,"renameCreatedFolderFunctionality",CurrentFileName);
			Report.InsertOverallStatus(TempResultFile,"renameCreatedFolderFunctionality",CurrentFileName);
		
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}


}

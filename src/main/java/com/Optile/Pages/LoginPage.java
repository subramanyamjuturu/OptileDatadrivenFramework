package com.Optile.Pages;

import java.util.Map;

import com.Optile.Actions.Action;
import com.Optile.Results.ResultsClass;
import com.Optile.Scripts.ExcelOperations;
import com.Optile.Scripts.ScriptExecuter;
import com.Optile.TestNg.Testng;

public class LoginPage  extends Testng {
	
	static String ObjectProprty;
	static String ObjectProprtyValue;


	static ResultsClass Report = new ResultsClass();
	static Action actions = new Action();
	static ExcelOperations fileoperations = new ExcelOperations();
	
	public void Login() 
	{
		try {
			
		ScriptExecuter.storeObjectProperteyValues();
		

		ScriptExecuter.storeObjectProperties();

		// getting object properties and driver details from Data sheet and storing in Collections
		Map<String,String> data =fileoperations.readData(CurrentFileName, "OpticalTaskLogin", 1);
		Map<String,String> Logindata =fileoperations.readData(CurrentFileName, "LoginPage", 1);
		
		String Username= Logindata.get("Username");
		String Password= Logindata.get("Password");
		String InvalidUsername= Logindata.get("InvalidUsername");
		String InvalidPassword= Logindata.get("InvalidPassword");
	
		//This is for Launching Saloodo site and inside this method Detailed Report code is available.
		actions.browserLaunch(Logindata.get("URL_Data"), driver, TempResultFile, "Launching Browser");


		String source =actions.pageSource(driver);

		if(source.contains("xml:lang=\"de\""))
		{
			actions.Click("GermanSignInButton", "GermanSignInButton", "SignIn button", driver, TempResultFile, "Clicking on Sign In");

			actions.Click("LoginInButton", "LoginInButton", "Login Button", driver, TempResultFile, "Clicking on Login Button");

			actions.VerifyText("ErrorMsg", "ErrorMsg", data.get("GermanErrorMsg"), driver, TempResultFile, "verifying error message without entering credentials");

			loginUser(Username, InvalidPassword);

			//Verifying Password Error Msg in update data sheet and change in script
			actions.VerifyText("ErrorMsg", "ErrorMsg", data.get("GermanIncorrectloginErrorMsg"), driver, TempResultFile, "Verifying Password Error Msg");


		}
		else if(source.contains("xml:lang=\"en\"")){

			actions.Click("EnglishSignInButton", "EnglishSignInButton", "SignIn button", driver, TempResultFile, "Clicking on Sign In");

			actions.Click("LoginInButton", "LoginInButton", "Login Button", driver, TempResultFile, "Clicking on Login Button");

			actions.VerifyText("ErrorMsg", "ErrorMsg", data.get("EnglishErrorMsg"), driver, TempResultFile, "verifying error message without entering credentials");

			loginUser(Username, InvalidPassword);

			//Verifying Password Error Msg in update data sheet and change in script
			actions.VerifyText("ErrorMsg", "ErrorMsg", data.get("EnglishIncorrectloginErrorMsg"), driver, TempResultFile, "Verifying Password Error Msg");

		}

		loginUser(Username, Password);

		VerifyUserLogged();

		actions.Click("Logout", "Logout", "Logout Button", driver, TempResultFile, "Clicking on Logout Button");


		Report.FinalResultWrite(TempResultFile,"OpticalTaskLogin",CurrentFileName);
		Report.InsertOverallStatus(TempResultFile,"OpticalTaskLogin",CurrentFileName);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public void loginUser(String Username,String Password) throws Exception
	{
		actions.ClearTex("emailid","emailid",driver);
		//Entering Email Id
		actions.enterText("emailid", "emailid", Username, driver, TempResultFile, "Entering Email Id");

		actions.ClearTex("Password","Password",driver);
		actions.enterPassword("Password", "Password", Password, driver, TempResultFile, "Entering Password");

		//clicking on Logout
		actions.Click("LoginInButton", "LoginInButton", "LoginInButton", driver, TempResultFile, "clicking on LoginIn Button");
	}

	public void VerifyUserLogged() throws Exception
	{
		// getting object properties and driver details from Data sheet and storing in Collections
		Map<String,String> data =fileoperations.readData(CurrentFileName, "OpticalTaskLogin", 1);

		//Verifying User is logged to application
		actions.VerifyText("Home", "Home", data.get("LoggedUserProfile"), driver, TempResultFile, "Verifying User is logged to application");

		//clicking on UserIcon
		actions.Click("UserIcon", "UserIcon", "UserIcon", driver, TempResultFile, "clicking UserIcon");

		//Verifying User is logged to application
		actions.VerifyText("ChangePhoto", "ChangePhoto", data.get("Change photo"), driver, TempResultFile, "Verifying User logged to DropBox");

	}

}

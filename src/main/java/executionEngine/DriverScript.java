package executionEngine;

import static executionEngine.DriverScript.OR;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Timestamp;
//import org.apache.xmlbeans.impl.util.Base64;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
//import javax.imageio.ImageIO;

//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.IOUtils;
import org.apache.log4j.xml.DOMConfigurator;
//import org.jboss.netty.handler.codec.base64.Base64Encoder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtils;
import utility.Log;
import utility.SendMail;

public class DriverScript extends ActionKeywords {

	public static Properties OR;
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method method[];

	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static String sData;
	public static boolean bResult;
	public static String Message;
	public static int TestStep;

	public static ExtentReports extent;
	public static ExtentReports emailextent;
	public static final String projdir = System.getProperty("user.dir");
	public static String filePath = projdir;
	public static ExtentTest test;
	public static ExtentTest emailtest;
	public static String ReportfilePath;
	public static String EmailReportfilePath;

	public static Date d = new Date();
	public static Timestamp t = new Timestamp(d.getTime());
	@SuppressWarnings("deprecation")
	public static Timestamp date1 = new Timestamp(d.getDate());
	public static String timeStamp = t.toString();
	public static String timeStamp2 = date1.toString();
	public static String dateWithoutTime = d.toString().substring(0, 10);
	public static String dateWithTime = d.toString();

	public static String RetryCount;

	public static int NumberofRetry;

	public DriverScript() throws NoSuchMethodException, SecurityException {
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();
	}

	public static void main(String[] args) throws Exception {

		ExcelUtils.setExcelFile(Constants.Path_TestData);
		DOMConfigurator.configure("log4j.xml");
		String Path_OR = Constants.Path_OR;
		FileInputStream fs = new FileInputStream(Path_OR);
		OR = new Properties(System.getProperties());
		OR.load(fs);

		// RetryCount = OR.getProperty("RetryCount");
		// NumberofRetry = Integer.parseInt(RetryCount);

		dateWithoutTime = dateWithoutTime.replace(' ', '_');
		dateWithoutTime = dateWithoutTime.replace(':', '_');
		dateWithTime = dateWithTime.replace(' ', '_');
		dateWithTime = dateWithTime.replace(':', '_');

		// ReportfilePath = "index.html";
		ReportfilePath = projdir + "\\dentoclik_DetailedTestReport_" +"\\Detailed_TestReport"+ dateWithTime + ".html";
		EmailReportfilePath = projdir + "\\dentoclik_EmailableTestReport_" + "\\Emailable_TestReport_" + dateWithTime
				+ ".html";
		extent = new ExtentReports(ReportfilePath, true);

		emailextent = new ExtentReports(EmailReportfilePath, true);

		System.out.println(ReportfilePath);
		deleteFolder(Constants.downloadpath);

		createfolder(Constants.dir + "\\screenshots\\" + dateWithoutTime);
		createfolder(Constants.downloadpath);

		DriverScript startEngine = new DriverScript();
		startEngine.execute_TestCase();
		emailextent.endTest(test);
		emailextent.flush();
		extent.endTest(test);
		extent.flush();
		Thread.sleep(2000);
		//SendMail.SendEmail("G3 Test Result", "Please see attachment for automation test result");

	}

	private void execute_TestCase() throws Exception {
		int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
		for (int iTestcase = 1; iTestcase < iTotalTestCases; iTestcase++) {
			bResult = true;
			sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases);
			sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode, Constants.Sheet_TestCases);
			if (sRunMode.equals("Yes")) {

				Log.startTestCase(sTestCaseID);
				iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
				iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
				test = extent.startTest(sTestCaseID, "Below steps are verified");
				emailtest = emailextent.startTest(sTestCaseID, "Below steps are verified");

				/*
				 * if(!sTestCaseID.contentEquals("Open_site")) {
				 * driver.get(OR.getProperty("SiteURL2")); }
				 */

				bResult = true;
				for (; iTestStep < iTestLastStep; iTestStep++) {
					sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,
							Constants.Sheet_TestSteps);
					sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject,
							Constants.Sheet_TestSteps);
					sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
					execute_Actions();
					if (bResult == false) {
						if (sActionKeyword.contains("Verify") || sActionKeyword.contains("AlertAccept")) {
							ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestcase, Constants.Col_Result,
									Constants.Sheet_TestCases);
							Log.endTestCase(sTestCaseID);
							// bResult=true;
						} else {
							ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestcase, Constants.Col_Result,
									Constants.Sheet_TestCases);
							Log.endTestCase(sTestCaseID);
							// break;
							// bResult=true;
						}
						// bResult=true;
					}
				}
				if (bResult == true) {
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestcase, Constants.Col_Result,
							Constants.Sheet_TestCases);
					Log.endTestCase(sTestCaseID);
				}
			}
		}
	}

	private static void execute_Actions() throws Exception {

		// System.out.println(NumberofRetry);

		for (int i = 0; i < method.length; i++) {

			String sRunTS = ExcelUtils.getCellData(iTestStep, Constants.Col_RunTS, Constants.Sheet_TestSteps);

			if (sRunTS.equals("Yes"))

			{

				if (method[i].getName().equals(sActionKeyword)) {
					readconstants2(sData);
					method[i].invoke(actionKeywords, sPageObject, sData);
					if (bResult == true) {
						ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult,
								Constants.Sheet_TestSteps);
						ExcelUtils.setCellData(DriverScript.Message, iTestStep, Constants.Col_Comment,
								Constants.Sheet_TestSteps);
						String TestSteps = ExcelUtils.getCellData(iTestStep, Constants.Col_Description,
								Constants.Sheet_TestSteps) + " --->> "
								+ ExcelUtils.getCellData(iTestStep, Constants.Col_Comment, Constants.Sheet_TestSteps);

						if (TestSteps == null || TestSteps.contains("Wait")) {
							break;
						} else {
							if (sActionKeyword.contains("Verify") || sActionKeyword.contains("AlertAccept")||sActionKeyword.contains("input")||sActionKeyword.contains("click")) {
								test.log(LogStatus.PASS, TestSteps);
								emailtest.log(LogStatus.PASS, TestSteps);
								String TestCaseName = ExcelUtils.getCellData(iTestStep, Constants.Col_TestCaseName,
										Constants.Sheet_TestSteps);
								String TestStepID = ExcelUtils.getCellData(iTestStep, Constants.Col_TestStepID,
										Constants.Sheet_TestSteps);
								capturescreenshot(TestCaseName, TestStepID);
								break;
							} else {
								test.log(LogStatus.PASS, TestSteps);
								emailtest.log(LogStatus.PASS, TestSteps);
								break;
							}
						}

					} else {
						/*
						 * for(;NumberofRetry>=1;) //if(NumberofRetry>=1) {
						 * System.out.println("Retry "); NumberofRetry--; String TestCaseName =
						 * ExcelUtils.getCellData(iTestStep, Constants.Col_TestCaseName,
						 * Constants.Sheet_TestSteps); String TestStepDesc =
						 * ExcelUtils.getCellData(iTestStep, Constants.Col_Description,
						 * Constants.Sheet_TestSteps); System.out.println(TestCaseName);
						 * System.out.println(TestStepDesc); System.out.println(NumberofRetry);
						 * execute_Actions(); }
						 * 
						 * System.out.println("Retry Finished"); NumberofRetry =
						 * Integer.parseInt(RetryCount);
						 */
						ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult,
								Constants.Sheet_TestSteps);
						ExcelUtils.setCellData(DriverScript.Message, iTestStep, Constants.Col_Comment,
								Constants.Sheet_TestSteps);
						String TestCaseName = ExcelUtils.getCellData(iTestStep, Constants.Col_TestCaseName,
								Constants.Sheet_TestSteps);
						String TestStepID = ExcelUtils.getCellData(iTestStep, Constants.Col_TestStepID,
								Constants.Sheet_TestSteps);
						String TestStepDesc = ExcelUtils.getCellData(iTestStep, Constants.Col_Description,
								Constants.Sheet_TestSteps);
						test.log(LogStatus.FAIL, DriverScript.Message);
						emailtest.log(LogStatus.FAIL, DriverScript.Message);
						System.out.println(TestCaseName);
						System.out.println(TestStepDesc);
						capturescreenshot(TestCaseName, TestStepID);

						// ActionKeywords.closeBrowser("","");
						break;
					}
				}
			}
		}
	}

	public static void deleteFolder(String deletepath) throws IOException {
		FileUtils.deleteDirectory(new File(deletepath));
	}

	public static String readconstants2(String Data) {
		if (sData.matches(
				"Browser|siteURL|userName|password|OTP|firstName|lastName|DOB|DDValue|add_successMsg|del_successMsg"))
			sData = OR.getProperty(Data);
		return sData;
	}

	public static void capturescreenshot(String TCName, String TSID) throws IOException, InterruptedException {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File screenshotLocation = new File(Constants.dir + "\\screenshots\\" + dateWithoutTime + "\\" + TCName + "_"
				+ TSID + "_" + dateWithTime + ".png");
		FileUtils.copyFile(screenshot, screenshotLocation);
		Thread.sleep(2000);
		InputStream is = new FileInputStream(screenshotLocation);
		byte[] imageBytes = IOUtils.toByteArray(is);
		Thread.sleep(2000);
		String base64 = Base64.getEncoder().encodeToString(imageBytes);
		test.log(LogStatus.INFO, "Snapshot below: " + test.addBase64ScreenShot(" data:image/png;base64," + base64));

	}

	public static void createfolder(String folderpath) {
		File theDir = new File(folderpath);
		if (!theDir.exists()) {
			boolean result = false;
			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
				System.out.println(se);
			}
			if (result) {
				System.out.println("DIR created");
			}
		}
	}

}
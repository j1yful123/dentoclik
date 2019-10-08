package config;

public class Constants {
	
	public static int NumberofRetry;
	//Path
	public static final String dir = System.getProperty("user.dir");
	public static final String downloadpath = dir+"\\downloadedfile"; 
	public static String filePath = dir;
	
	public static final String Path_TestData = dir+"//DataEngine_dentoclik.xls";
	public static final String Path_OR = dir+"//dentoclik.txt";
	public static final String File_TestData = "DataEngine_dentoclik.xls";
	
	
	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";
	
	
	//Data Sheet Column Numbers
	public static final int Col_TestCaseID = 0;	
	public static final int Col_TestScenarioID =1 ;
	public static final int Col_PageObject =4 ;
	public static final int Col_ActionKeyword =5 ;
	public static final int Col_RunMode =2 ;
	public static final int Col_Result =3 ;
	public static final int Col_DataSet =6 ;
	public static final int Col_TestStepResult = 8 ;
	public static final int Col_Comment = 9 ;
	public static final int Col_Description= 2;
	public static final int Col_TestCaseName= 0;
	public static final int Col_TestStepID= 1;
	public static final int Col_RunTS = 7;
	
	
	// Data Engine Excel sheets
	public static final String Sheet_TestSteps = "Test Steps";
	public static final String Sheet_TestCases = "Test Cases";
	public static final String Sheet_TestData = "Test Data";
	
}

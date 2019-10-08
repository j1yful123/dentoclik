package config;

import static executionEngine.DriverScript.OR;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

import executionEngine.DriverScript;
import utility.Log;
import utility.SendMail;

public class ActionKeywords {
	//Generate Random Number
	static String firstName = "";
	static String lastName = "";
		static String s1 = "";
	    static Random rand = new Random();
	    static int Low = 10;
	    static int High = 100;
	    static int diceRoll = rand.nextInt(High - Low) + Low;
  //End Generate Random Number
   	protected static WebDriver driver = null;
	static String mainwindow;

	public static void openBrowser(String object, String data) {
		
		Log.info("Opening Browser");
		try {
			if (data.equals("Mozilla")) {

				DesiredCapabilities capabilities = DesiredCapabilities.firefox();

				FirefoxProfile profile = new FirefoxProfile();

				profile.setPreference("browser.download.folderList", 2);
				profile.setPreference("browser.download.dir", Constants.downloadpath);
				profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
				profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
						"application/msword, application/csv, application/ris, text/csv, image/png, image/jpeg, application/pdf, text/html, text/plain, application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream");
				profile.setPreference("browser.download.manager.showWhenStarting", false);
				profile.setPreference("browser.download.manager.focusWhenStarting", false);
				profile.setPreference("browser.download.useDownloadDir", true);
				profile.setPreference("browser.helperApps.alwaysAsk.force", false);
				profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
				profile.setPreference("browser.download.manager.closeWhenDone", true);
				profile.setPreference("browser.download.manager.showAlertOnComplete", false);
				profile.setPreference("browser.download.manager.useWindow", false);
				profile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
				profile.setPreference("pdfjs.disabled", true);

				capabilities.setCapability(FirefoxDriver.PROFILE, profile);

				driver = new FirefoxDriver(capabilities);

				// driver = new FirefoxDriver();

				driver.manage().window().maximize();
				Log.info("Mozilla browser started");
			} else if (data.equals("IE")) {
				// Need to specify exe
				driver = new InternetExplorerDriver();
				driver.manage().window().maximize();
				Log.info("IE browser started");
			} else if (data.equals("Chrome")) {
				// Need to specify exe
				System.setProperty("webdriver.chrome.driver", Constants.dir + "//chromedriver.exe");
				System.out.println("in chrome");
				driver = new ChromeDriver();
				driver.manage().window().maximize();
				Log.info("Chrome browser started");
				
			}
			else if (data.equals("chromeHeadless")) {
				System.setProperty("webdriver.chrome.driver", Constants.dir + "//chromedriver.exe");
				ChromeOptions cap = new ChromeOptions();
				cap.addArguments("--headless","--disable-gpu","window-size=1368,768");
				System.out.println("in headless chrome");
				driver = new ChromeDriver(cap);
				driver.manage().window().maximize();
				
			}

			Thread.sleep(3000);
			int implicitWaitTime = (30);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			DriverScript.bResult=true;
			DriverScript.Message = "Opening " +data+" Browser";
		} catch (Exception e) {
			Log.info("Not able to open the Browser --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.Message = "Not able to open " +data+" Browser";
		}
	}

	// Navigate
	public static void navigate(String object, String data) {
		try {
			Log.info("Navigating to URL");
			driver.get(data);
			DriverScript.Message = "Navigating to URL " + data;
			DriverScript.bResult = true;
			Thread.sleep(3000);
			
		} catch (Exception e) {
			Log.info("Not able to navigate --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.Message = "Not able to navigate --- " + e.getMessage();
		}
	}

	public static void navigateBack(String object, String data) {
		try {
			Log.info("Navigating back");
			Thread.sleep(2000);
			// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.navigate().back();
			DriverScript.Message = "Navigating back ";
			Thread.sleep(2000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.info("Not able to navigate back--- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.Message = "Not able to navigate back--- " + e.getMessage();
		}
	}

	
	// Generate Random FirstName
		public static void RandomFirstName(String object, String Message) {
			try {
				Log.info("Generate Random FirstName");

				Faker faker = new Faker();
				firstName = faker.name().firstName();
				System.out.println(firstName);
				driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(firstName);

				DriverScript.bResult = true;
				DriverScript.Message = "First Name is " + firstName;

			} catch (Exception e) {
				Log.error("Fail to Generate Random FirstName --- " + e.getMessage());
				DriverScript.Message = "Fail to Generate Random FirstName --- " + e.getMessage();
				DriverScript.bResult = false;
			}
		}

		// Generate Random LastName
		public static void RandomLastName(String object, String Message) {
			try {
				Log.info("Generate Random LastName");
				Faker faker = new Faker();
				lastName = faker.name().lastName();
				System.out.println(lastName);
				driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(lastName);
				DriverScript.bResult = true;
				DriverScript.Message = "Last Name is " + lastName;
			} catch (Exception e) {
				Log.error("Fail to Generate Random LastName --- " + e.getMessage());
				DriverScript.Message = "Fail to Generate Random LastName --- " + e.getMessage();
				DriverScript.bResult = false;
			}
		}

		public static void RandomEmail(String object, String Message) {
			try {
				Log.info("Generate Random LastName");
				Faker faker = new Faker();
				lastName = faker.name().lastName();
				System.out.println(lastName);
				driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(firstName + "@gmail.com");
				DriverScript.bResult = true;
				DriverScript.Message = "Last Name is " + lastName;

			} catch (Exception e) {
				Log.error("Fail to Generate Random LastName --- " + e.getMessage());
				DriverScript.Message = "Fail to Generate Random LastName --- " + e.getMessage();
				DriverScript.bResult = false;
			}
		}
	/*
	 * public static void navigateLogOut(String object, String data){ try{
	 * Log.info("Navigating to LogOut URL");
	 * driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	 * driver.get(Constants.LogOutURL); DriverScript.Message =
	 * "Navigating to LogOut URL"; Thread.sleep(3000); }catch(Exception e){
	 * Log.info("Not able to navigate --- " + e.getMessage());
	 * DriverScript.Message = "Not able to navigate --- " + e.getMessage();
	 * DriverScript.bResult = false; } }
	 * 
	 * public static void navigateContactUs(String object, String data){ try{
	 * Log.info("Navigating to Contact Us URL");
	 * driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	 * driver.get(Constants.ContactUsURL); DriverScript.Message =
	 * "Navigating to Contact Us URL"; Thread.sleep(3000); }catch(Exception e){
	 * Log.info("Not able to navigate to Contact us page --- " +
	 * e.getMessage()); DriverScript.Message = "Not able to navigate --- " +
	 * e.getMessage(); DriverScript.bResult = false; } }
	 */
	// Click Actions
	public static void clickByClass(String object, String data) {
		try {
			Log.info("Clicking on Web element " + object);
			driver.findElement(By.className(OR.getProperty(object))).click();
			DriverScript.Message = "Clicking on Web element " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.Message = "Not able to click --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void clickbyXpath(String object, String data) {
		try {
			Log.info("Clicking on Web element " + object);
		WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));	
		System.out.println(""+element);
		element.click();
			DriverScript.Message = "Clicking on Web element " ;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.Message = "Not able to click --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void Submit(String object, String data) {
		try {
			Log.info("Clicking on Web element " + object);
			driver.findElement(By.xpath(OR.getProperty(object))).submit();
			DriverScript.Message = "Clicking on Web element " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.Message = "Not able to click --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void clickByCSSSelector(String object, String data) {
		try {
			Log.info("Clicking on Web element " + object);
			driver.findElement(By.cssSelector(OR.getProperty(object))).click();
			DriverScript.Message = "Clicking on Web element " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.Message = "Not able to click --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void clickByPartiallink(String object, String data) {
		try {
			Log.info("Clicking on Webelement " + object);
			driver.findElement(By.partialLinkText(OR.getProperty(object))).click();
			DriverScript.Message = "Clicking on Web element " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.Message = "Not able to click --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void clickByText(String object, String data) {
		try {
			Log.info("Clicking on Webelement " + object);

			driver.findElement(By.xpath(OR.getProperty(object) + "[contains(.,'" + data + "')]")).click();
			DriverScript.Message = "Clicking on Web element " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.Message = "Not able to click --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void clickByName(String object, String data) {
		try {
			Log.info("Clicking on Webelement " + object);

			driver.findElement(By.name(OR.getProperty(data))).click();
			DriverScript.Message = "Clicking on Web element " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.Message = "Not able to click --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}
	public static void clickByTextLast(String object, String data) {
		try {
			Log.info("Clicking on Webelement " + object);

			driver.findElement(By.xpath(("(" + OR.getProperty(object) + "[contains(.,'" + data + "')])") + "[last()]"))
					.click();

			DriverScript.Message = "Clicking on Web element " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.Message = "Not able to click --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void clickByTextJS(String object, String data) {
		try {
			Log.info("Clicking on Webelement " + object);
			WebElement element = driver
					.findElement(By.xpath(("(" + OR.getProperty(object) + "[contains(.,'" + data + "')])")));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			DriverScript.Message = "Clicking on Web element " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.Message = "Not able to click --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void clickByTextJSLast(String object, String data) {
		try {
			Log.info("Clicking on Webelement " + object);
			WebElement element = driver.findElement(
					By.xpath(("(" + OR.getProperty(object) + "[contains(.,'" + data + "')])") + "[last()]"));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			DriverScript.Message = "Clicking on Web element " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.Message = "Not able to click --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void clickByTitle(String object, String data) {
		try {
			Log.info("Clicking on Webelement " + object);
			driver.findElement(By.xpath(OR.getProperty(object) + "[contains(@data-original-title,'" + data + "')]"))
					.click();
			DriverScript.Message = "Clicking on Web element " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.Message = "Not able to click --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void ClickMoveMouse(String object, String data) {
		try {
			Log.info("Moving over element" + object);
			Thread.sleep(2000);
			WebElement searchBtn = driver.findElement(By.xpath(OR.getProperty(object)));
			Actions action = new Actions(driver);
			action.moveToElement(searchBtn).click().perform();
			DriverScript.Message = "Moving over element" + object;
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to move mouse --- " + e.getMessage());
			DriverScript.Message = "Not able to move mouse --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void ClickByJSXpath(String object, String data) {
		try {
			Log.info("Click on Web element" + object);
			Thread.sleep(2000);

			WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			// WebDriverWait wait = new WebDriverWait(driver, 3);
			// wait.until(ExpectedConditions.alertIsPresent());
			// Alert alert = driver.switchTo().alert();
			// alert.accept();
			DriverScript.Message = "Click on Web element" + object;
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.Message = "Not able to click --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Set Stlye
	public static void SetStyle(String object, String data) {
		try {
			Log.info("Set Style of Web element" + object);
			Thread.sleep(2000);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
			js.executeScript("arguments[0].setAttribute('style', 'width: 40%')", element);
			DriverScript.Message = "Set Stlye of Web element" + object;
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to set stlye --- " + e.getMessage());
			DriverScript.Message = "Not able to set style --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Alert
	public static void AlertAccept(String object, String data) {
		try {
			Log.info("Accepting Alert" + object);
			System.out.println("Test1");
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.alertIsPresent());
			System.out.println("Test1");
			Alert alert = driver.switchTo().alert();
			alert.accept();
			DriverScript.Message = "Alert Accepted --- ";
			DriverScript.bResult = true;

		} catch (Exception e) {
			Log.error("Not able to accept alert --- " + e.getMessage());
			System.out.println("Test2");
			DriverScript.Message = "Not able to accept alert --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void AlertDismiss(String object, String data) {
		try {
			Log.info("Dismissing alert" + object);
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			DriverScript.Message = "Alert Dismissed --- ";
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to dismiss alert --- " + e.getMessage());
			DriverScript.Message = "Not able to dismiss alert --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Input
	public static void input(String object, String data) {
		try {
			if(object.equals("Login_email_add")){
				driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("autoemail" + diceRoll + "@yopmail.com");
			}
			else{
			Log.info("Entering the text in " + object);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			}
			DriverScript.Message = "Entering the input " + data + " in the field" ;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to Enter  --- " + e.getMessage());
			DriverScript.Message = "Not able to Enter  --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void inputByCSS(String object, String data) {
		try {
			Log.info("Entering the text in " + object);
			driver.findElement(By.cssSelector(OR.getProperty(object))).sendKeys(data);
			DriverScript.Message = "Entering the text in  " + data + " in the " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to Enter UserName --- " + e.getMessage());
			DriverScript.Message = "Not able to Enter UserName --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Clear Text box
	public static void clear(String object, String data) {
		try {
			Log.info("Clearing text in " + object);
			WebElement ClearEle = driver.findElement(By.xpath(OR.getProperty(object)));
			ClearEle.sendKeys(Keys.CONTROL + "a");
			ClearEle.sendKeys(Keys.DELETE);
			// driver.manage().deleteAllCookies();
			// ClearEle.getText().length();
			DriverScript.Message = "Clearing text in " + object;
			Thread.sleep(500);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to clear text --- " + e.getMessage());
			DriverScript.Message = "Not able to clear text --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void clearByCSS(String object, String data) {
		try {
			Log.info("Clearing text in " + object);
			driver.findElement(By.cssSelector(OR.getProperty(object))).clear();
			DriverScript.Message = "Clearing text in " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to clear text --- " + e.getMessage());
			DriverScript.Message = "Not able to clear text --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Drop Down
	public static void SelectDDByValue(String object, String data) {
		try {
			Thread.sleep(2000);
			Log.info("Selecting drop down from the " + object);
			Select selectByValue = new Select(driver.findElement(By.xpath(OR.getProperty(object))));
			selectByValue.selectByValue(data);
			DriverScript.Message = "Selecting drop down value " + data + " from the " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to select DD value --- " + e.getMessage());
			DriverScript.Message = "Not able to select DD value --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void SelectDDByValueCSS(String object, String data) {
		try {
			Thread.sleep(2000);
			Log.info("Selecting drop down from the " + object);
			Select selectByValue = new Select(driver.findElement(By.cssSelector(OR.getProperty(object))));
			selectByValue.selectByValue(data);
			DriverScript.Message = "Selecting drop down from the " + data + " from the " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to select DD value --- " + e.getMessage());
			DriverScript.Message = "Not able to select DD value --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Drop Down
	public static void SelectDDByVisibleText(String object, String data) {
		try {
			Thread.sleep(2000);
			Log.info("Selecting drop down from the " + object);
			Select selectByValue = new Select(driver.findElement(By.xpath(OR.getProperty(object))));
			selectByValue.selectByVisibleText(data);
			DriverScript.Message = "Selecting drop down value " + data + " from the " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to select DD value --- " + e.getMessage());
			DriverScript.Message = "Not able to select DD value --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Wait for 3 seconds
	public static void waitFor3sec(String object, String data) throws Exception {
		try {
			Log.info("Wait for 3 seconds");
			Thread.sleep(3000);
			DriverScript.Message = "Wait for 3 seconds";
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to Wait --- " + e.getMessage());
			DriverScript.Message = "Not able to Wait --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Wait for 5 seconds
	public static void waitFor5sec(String object, String data) throws Exception {
		try {
			Log.info("Wait for 5 seconds");
			Thread.sleep(5000);
			DriverScript.Message = "Wait for 5 seconds";
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to Wait --- " + e.getMessage());
			DriverScript.Message = "Not able to Wait --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Wait for 10 seconds
	public static void waitFor10sec(String object, String data) throws Exception {
		try {
			Log.info("Wait for 10 seconds");
			Thread.sleep(10000);
			DriverScript.Message = "Wait for 10 seconds";
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to Wait --- " + e.getMessage());
			DriverScript.Message = "Not able to Wait --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Wait for 15 seconds
	public static void waitFor15sec(String object, String data) throws Exception {
		try {
			Log.info("Wait for 15 seconds");
			Thread.sleep(10000);
			DriverScript.Message = "Wait for 15 seconds";
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to Wait --- " + e.getMessage());
			DriverScript.Message = "Not able to Wait --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Wait for 20 seconds
	public static void waitFor20sec(String object, String data) throws Exception {
		try {
			Log.info("Wait for 20 seconds");
			Thread.sleep(10000);
			DriverScript.Message = "Wait for 20 seconds";
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to Wait --- " + e.getMessage());
			DriverScript.Message = "Not able to Wait --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Close Browser
	public static void closeBrowser(String object, String data) {
		try {
			Log.info("Closing the browser");
			driver.quit();
			DriverScript.Message = "Closing the browser";
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to Close the Browser --- " + e.getMessage());
			DriverScript.Message = "Not able to Close the Browser --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Close Tab
	/*
	 * public static void closeTab(String object, String data){ try{ Log.info(
	 * "Closing the Tab");
	 * //driver.findElement(By.id("pagekey-public_profile_v3_desktop")).sendKeys
	 * (Keys.CONTROL + "w"); driver.close(); DriverScript.Message =
	 * "Closing the Tab"; DriverScript.bResult=true; }catch(Exception e){
	 * Log.error("Not able to Close the Tab --- " + e.getMessage());
	 * DriverScript.Message = "Not able to Close the Tab --- " + e.getMessage();
	 * DriverScript.bResult = false; } }
	 */
	public static void closeTab(String object, String data) {
		Robot robot = null;
		String mainwindow = driver.getWindowHandle();
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_W);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_W);
		driver.switchTo().window(mainwindow);
	}

	// Closed Current Tab
	public static void closeTabs(String object, String data) {
		try {
			Log.info("Clicking on Web element " + object);
			String originalHandle = driver.getWindowHandle();

			// Do something to open new tabs

			for (String handle : driver.getWindowHandles()) {
				if (!handle.equals(originalHandle)) {
					driver.switchTo().window(handle);
					driver.close();
				}
			}

			driver.switchTo().window(originalHandle);
			DriverScript.Message = "Clicking on Web element " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.Message = "Not able to click --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Hover without click
	public static void Hoverwithoutclick(String object, String data) {
		try {
			Log.info("Moving over element" + object);
			Thread.sleep(2000);
			WebElement hover = driver.findElement(By.xpath(OR.getProperty(object)));
			Actions action = new Actions(driver);
			// action.moveToElement(hover).click().perform();
			action.moveToElement(hover).build().perform();
			DriverScript.Message = "Moving over element" + object;
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to move mouse --- " + e.getMessage());
			DriverScript.Message = "Not able to move mouse --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Move Mouse
	public static void MoveMouse(String object, String data) {
		try {
			Log.info("Moving over element" + object);
			WebElement searchBtn = driver.findElement(By.className(OR.getProperty(object)));
			Actions action = new Actions(driver);
			action.moveToElement(searchBtn).perform();
			DriverScript.Message = "Moving over element" + object;
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to move mouse --- " + e.getMessage());
			DriverScript.Message = "Not able to move mouse --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Move Mouse by 10 pixel
	public static void MoveMouseHorizontal(String object, String data) {
		try {
			Log.info("Moving over element" + object);
			Point coordinates = driver.findElement(By.xpath(OR.getProperty(object))).getLocation();
			Robot robot = new Robot();
			robot.mouseMove(coordinates.getX(), coordinates.getY() + 15);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to move mouse --- " + e.getMessage());
			DriverScript.Message = "Not able to move mouse --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Verification
	public static void VerifyTextPresentByTagName(String object, String data) {
		try {
			Log.info("Verifying text is present " + object);
			System.out.println("Test12");
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(OR.getProperty(object))));

			// String bodyText =
			// driver.findElement(By.tagName(OR.getProperty(object))).getText();
			String bodyText = el.getText();
			String bodyText_lower = bodyText.toLowerCase();
			// System.out.println(bodyText_lower);
			String data_lower = data.toLowerCase();
			if (bodyText_lower.contains(data_lower)) {
				DriverScript.bResult = true;
			} else {
				throw new Exception();
			}
			DriverScript.Message = "Verifying text " + data + " is present for " + object;
			Thread.sleep(3000);
		} catch (Exception e) {
			Log.error("Not able to Verify Text by TagName--- " + e.getMessage());
			DriverScript.Message = "Not able to Verify Text by TagName--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void VerifyTextPresentByXpath(String object, String data) {
		try {
			Log.info("Verifying text is present " + object);
			System.out.println("Step is Verified");
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(object))));
			// assertTrue(el.getText().contains("Successfully saved"));
            System.out.println(" "+ el);
			String bodyText = el.getText();

			// String bodyText =
			// driver.findElement(By.xpath(OR.getProperty(object))).getText();
			String bodyText_lower = bodyText.toLowerCase();
			String data_lower = data.toLowerCase();
			System.out.println(bodyText_lower);
			if (bodyText_lower.contains(data_lower)) {
				DriverScript.bResult = true;
				DriverScript.Message = "Verifying text " + data + " is present for " + object;
			} else {
				throw new Exception();
			}
			Thread.sleep(3000);
		} catch (Exception e) {
			Log.error("Not able to Verify Text --- " + e.getMessage());
			DriverScript.Message = "Not able to Verify Text --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void VerifyTextPresentByClassName(String object, String data) {
		try {
			Log.info("Verifying text is present for " + object);
			String bodyText = driver.findElement(By.className(OR.getProperty(object))).getText();
			String bodyText_lower = bodyText.toLowerCase();
			String data_lower = data.toLowerCase();
			if (bodyText_lower.contains(data_lower)) {
				DriverScript.bResult = true;
				DriverScript.Message = "Verifying text " + data + " is present for " + object;
			} else {
				throw new Exception();
			}
			Thread.sleep(3000);
		} catch (Exception e) {
			Log.error("Not able to Verify Text by ClassName--- " + e.getMessage());
			DriverScript.Message = "Not able to Verify Text by ClassName--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void VerifyTextPresentByCSS(String object, String data) {
		try {
			Log.info("Verifying text is present for " + object);
			String bodyText = driver.findElement(By.cssSelector(OR.getProperty(object))).getText();
			String bodyText_lower = bodyText.toLowerCase();
			String data_lower = data.toLowerCase();
			if (bodyText_lower.contains(data_lower)) {
				DriverScript.bResult = true;
				DriverScript.Message = "Verifying text " + data + " is present for " + object;
			} else {
				throw new Exception();
			}
			Thread.sleep(3000);
		} catch (Exception e) {
			Log.error("Not able to Verify Text by ClassName--- " + e.getMessage());
			DriverScript.Message = "Not able to Verify Text by ClassName--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void VerifyByURL(String object, String data) {
		try {
			Log.info("Verifying URL for " + object);
			String URL = driver.getCurrentUrl();
			String URL_lower = URL.toLowerCase();
			String data_lower = data.toLowerCase();
			if (URL_lower.contains(data_lower)) {
				DriverScript.bResult = true;
				DriverScript.Message = "Verifying URL " + data + " is present for " + object;
			} else {
				throw new Exception();
			}
			Thread.sleep(3000);
		} catch (Exception e) {
			Log.error("Not able to Verify by URL--- " + e.getMessage());
			DriverScript.Message = "Not able to Verify by URL--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void VerifyByImage(String object, String data) {
		try {
			Log.info("Verifying Image is displayed for " + object);

			WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
			String src = ((JavascriptExecutor) driver)
					.executeScript("return arguments[0].attributes['src'].value;", element).toString();
			String src_lower = src.toLowerCase();
			String data_lower = data.toLowerCase();
			if (src_lower.contains(data_lower)) {
				DriverScript.bResult = true;
				DriverScript.Message = "Verifying Image " + data + " is present for " + object;
			} else {
				throw new Exception();
			}
			Thread.sleep(3000);
		} catch (Exception e) {
			Log.error("Not able to Verify Image by Xpath--- " + e.getMessage());
			DriverScript.Message = "Not able to Verify Image by Xpath--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void VerifyByImageCSS(String object, String data) {
		try {
			Log.info("Verifying Image is displayed for " + object);
			WebElement element = driver.findElement(By.cssSelector(OR.getProperty(object)));
			String src = ((JavascriptExecutor) driver)
					.executeScript("return arguments[0].attributes['src'].value;", element).toString();
			String src_lower = src.toLowerCase();
			String data_lower = data.toLowerCase();
			if (src_lower.contains(data_lower)) {
				DriverScript.bResult = true;
				DriverScript.Message = "Verifying Image " + data + " is present for " + object;
			} else {
				throw new Exception();
			}
			Thread.sleep(3000);
		} catch (Exception e) {
			Log.error("Not able to Verify Image by CSS--- " + e.getMessage());
			DriverScript.Message = "Not able to Verify Image by CSS--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void VerifyFileDownloaded(String object, String data) {
		try {
			File dir = new File(Constants.downloadpath);
			System.out.println(Constants.downloadpath);
			System.out.println(data);
			File[] files = dir.listFiles();
			if (files == null || files.length == 0) {
				DriverScript.bResult = false;
				DriverScript.Message = "Not able to Verify file download--- ";
			}

			for (int i = 1; i < files.length; i++) {
				if (files[i].getName().contains(data)) {
					DriverScript.bResult = true;
					DriverScript.Message = "Uploaded Image " + data;
				}
			}
			Thread.sleep(3000);
		} catch (Exception e) {
			Log.error("Not able to Verify file download---  " + e.getMessage());
			DriverScript.Message = "Not able to Verify file download--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void VerifyLowPricefilter(String object, String data) {
		try {
			Log.info("Verifying low price filter for " + object);
			String Text = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			StringBuffer sBuffer = new StringBuffer();
			Pattern p = Pattern.compile("[0-9]+.[0-9]*|[0-9]*.[0-9]+|[0-9]+");
			Matcher m = p.matcher(Text);
			while (m.find()) {
				sBuffer.append(m.group());
			}
			String price1 = sBuffer.toString().replaceAll("\\s+", "");
			Float filter_price = Float.valueOf(data);
			Float price = Float.valueOf(price1);

			if (price >= filter_price) {
				DriverScript.bResult = true;
				DriverScript.Message = "Verifying low price " + data + " for " + object;
			} else {
				throw new Exception();
			}
			Thread.sleep(3000);
		} catch (Exception e) {
			Log.error("Not able to Verify low price filter--- " + e.getMessage());
			DriverScript.Message = "Not able to Verify low price filter--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void VerifyHighPricefilter(String object, String data) {
		try {
			Log.info("Verifying high price filter for " + object);
			String Text = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			StringBuffer sBuffer = new StringBuffer();
			Pattern p = Pattern.compile("[0-9]+.[0-9]*|[0-9]*.[0-9]+|[0-9]+");
			Matcher m = p.matcher(Text);
			while (m.find()) {
				sBuffer.append(m.group());
			}
			String price1 = sBuffer.toString().replaceAll("\\s+", "");
			Float filter_price = Float.valueOf(data);
			Float price = Float.valueOf(price1);

			if (price <= filter_price) {
				DriverScript.bResult = true;
				DriverScript.Message = "Verifying high price " + data + " for " + object;
			} else {
				DriverScript.bResult = false;
				DriverScript.Message = "Not able to Verify high price filter--- " + data + " for " + object;
			}
			Thread.sleep(3000);
		} catch (Exception e) {
			Log.error("Not able to Verify high price filter--- " + e.getMessage());
			DriverScript.Message = "Not able to Verify high price filter--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Verify Pricing
	public static void VerifyProductPrice(String object, String data) {
		try {
			Log.info("Verifying product price for " + object);

			// String maxquantity =
			// driver.findElement(By.xpath("(//div[contains(@class,
			// 'table-heading') and contains(.,
			// '+')])[last()]")).get(0).getText();
			String maxquantity = driver.findElements(By.xpath("//div[contains(@class, 'table-heading')]")).get(1)
					.getText();
			maxquantity = maxquantity.split("-")[0];
			System.out.println(maxquantity);
			String lowestprice = driver
					.findElement(
							By.xpath("(//div[contains(@class, 'table-content') and contains(., '$')])[position()=1]"))
					.getText();
			System.out.println(lowestprice);
			String maxquantity2 = maxquantity.replace("+", "");
			Float quantity = Float.valueOf(maxquantity2);
			System.out.println("Max Quantity:  " + quantity);

			StringBuffer sBuffer1 = new StringBuffer();
			Pattern p1 = Pattern.compile("[0-9]+.[0-9]*|[0-9]*.[0-9]+|[0-9]+");
			Matcher m1 = p1.matcher(lowestprice);
			while (m1.find()) {
				sBuffer1.append(m1.group());
			}
			String price1 = sBuffer1.toString().replaceAll("\\s+", "");
			Float price = Float.valueOf(price1);
			System.out.println("lowestprice:  " + price);

			float productprice = price * quantity;

			System.out.println(productprice);

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,250)", "");
			jse.executeScript("window.scrollBy(0,250)", "");

			String pricelabel = driver.findElement(By.xpath(OR.getProperty(object))).getText();

			StringBuffer sBuffer2 = new StringBuffer();
			Pattern p2 = Pattern.compile("[0-9]+.[0-9]*|[0-9]*.[0-9]+|[0-9]+");
			Matcher m2 = p2.matcher(pricelabel);
			while (m2.find()) {
				sBuffer2.append(m2.group());
			}
			String price2 = sBuffer2.toString().replaceAll("\\s+", "");
			Float pricelabel2 = Float.valueOf(price2);
			System.out.println("lowestprice2:  " + pricelabel2);

			JavascriptExecutor jse2 = (JavascriptExecutor) driver;
			jse2.executeScript("window.scrollBy(0,-250)", "");
			jse2.executeScript("window.scrollBy(0,-250)", "");

			if (pricelabel2 == productprice) {
				DriverScript.bResult = true;
				DriverScript.Message = "Verifying high price " + data + " for " + object;
			}

			Thread.sleep(3000);
		} catch (Exception e) {
			Log.error("Not able to Verify product price--- " + e.getMessage());
			DriverScript.Message = "Not able to Verify product price --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Enter Product Quantity
	public static void inputProductQty(String object, String data) {
		try {
			Log.info("Enter product quantity " + object);

			// String maxquantity =
			// driver.findElement(By.xpath("(//div[contains(@class,
			// 'table-heading') and contains(., '+')])[last()]")).getText();
			String maxquantity = driver.findElements(By.xpath("//div[contains(@class, 'table-heading')]")).get(1)
					.getText();
			maxquantity = maxquantity.split("-")[0];
			String maxquantity2 = maxquantity.replace("+", "");
			Float quantity = Float.valueOf(maxquantity2);
			System.out.println("Max Quantity:  " + quantity);

			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(maxquantity2);

			DriverScript.bResult = true;
			DriverScript.Message = "Enter product quantity  " + data + " for " + object;

			Thread.sleep(3000);
		} catch (Exception e) {
			Log.error("Not able to Enter product quantity--- " + e.getMessage());
			DriverScript.Message = "Not able to Enter product quantity--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Upload Image
	public static void UploadImage(String object, String data) {
		try {
			Log.info("Uploading Image for " + object);
			StringSelection stringSelection_filepath = new StringSelection(data);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection_filepath, null);
			Thread.sleep(500);
			System.out.println("File path" + data);
			try {

				Robot robot = new Robot();
				// Press CTRL+V
				robot.setAutoDelay(100);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				// Release CTRL+V
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);

				robot.setAutoDelay(100);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);

			} catch (AWTException e) {
				e.printStackTrace();
			}
			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Uploaded Image " + data;
		} catch (Exception e) {
			Log.error("Not able to upload image--- " + e.getMessage());
			DriverScript.Message = "Not able to upload image--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Scroll Up
	public static void ScrollUp(String object, String data) {
		try {
			Log.info("Scrolling Up " + object);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-250)", "");
			jse.executeScript("window.scrollBy(0,-250)", "");
			/*
			 * Robot robot = new Robot(); robot.keyPress(KeyEvent.VK_PAGE_UP);
			 * robot.keyPress(KeyEvent.VK_PAGE_UP);
			 * robot.keyPress(KeyEvent.VK_PAGE_UP);
			 */
			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Scroll Up " + object;
		} catch (Exception e) {
			Log.error("Not able to Scroll up--- " + e.getMessage());
			DriverScript.Message = "Not able to Scroll up--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void SingleScrollUp(String object, String data) {
		try {
			Log.info("Scrolling Up " + object);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-250)", "");
			/*
			 * Robot robot = new Robot(); robot.keyPress(KeyEvent.VK_PAGE_UP);
			 */
			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Scroll Up " + object;
		} catch (Exception e) {
			Log.error("Not able to Scroll up--- " + e.getMessage());
			DriverScript.Message = "Not able to Scroll up--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Scroll Down
	public static void ScrollDown(String object, String data) {
		try {
			Log.info("Scrolling Down " + object);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,250)", "");
			jse.executeScript("window.scrollBy(0,250)", "");
			jse.executeScript("window.scrollBy(0,250)", "");
			jse.executeScript("window.scrollBy(0,250)", "");
			jse.executeScript("window.scrollBy(0,250)", "");
			jse.executeScript("window.scrollBy(0,250)", "");
			/*
			 * Robot robot = new Robot(); robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			 * robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			 * robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			 */
			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Scroll Down " + object;
		} catch (Exception e) {
			Log.error("Not able to Scroll Down--- " + e.getMessage());
			DriverScript.Message = "Not able to Scroll Down--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Single Scroll Down
	public static void SingleScrollDown(String object, String data) {
		try {
			Log.info("Scrolling Down " + object);
			// JavascriptExecutor jse = (JavascriptExecutor)driver;
			// jse.executeScript("window.scrollBy(0,250)", "");

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(1000);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(1000);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(1000);
			DriverScript.bResult = true;
			DriverScript.Message = "Scroll Down " + object;
		} catch (Exception e) {
			Log.error("Not able to Scroll Down--- " + e.getMessage());
			DriverScript.Message = "Not able to Scroll Down--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Drag and Drop
	public static void DragAndDrop(String object, String data) {
		try {
			WebElement elementToMove = driver.findElement(By.xpath(OR.getProperty(object)));
			WebElement moveToElement = driver.findElement(By.xpath(data));

			Actions builder = new Actions(driver);
			Action dragAndDrop = builder.clickAndHold(elementToMove).moveToElement(moveToElement).release(moveToElement)
					.build();

			dragAndDrop.perform();

			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Drag and Drop " + object;
		} catch (Exception e) {
			Log.error("Not able to Drag and Drop--- " + e.getMessage());
			DriverScript.Message = "Not able to Drag and Drop--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Drag and Drop
	public static void Slide(String object, String data) {
		try {
			WebElement dragElementFrom = driver.findElement(By.xpath(OR.getProperty(object)));

			// To Move jQuery slider by 100 pixel offset using dragAndDropBy
			// method of Actions class.
			new Actions(driver).dragAndDropBy(dragElementFrom, 50, 0).build().perform();

			Thread.sleep(2000);

			// After 5 seconds, This will Move jQuery slider by 100 pixel offset
			// using the combination of clickAndHold, moveByOffset and release
			// methods of Actions class.
			// new
			// Actions(driver).clickAndHold(dragElementFrom).moveByOffset(50,0).release().perform();

			DriverScript.bResult = true;
			DriverScript.Message = "Slide " + object;
		} catch (Exception e) {
			Log.error("Not able to Slide--- " + e.getMessage());
			DriverScript.Message = "Not able to Slide--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	/*
	 * public static void WindowsPopUp(String object, String data){ try{
	 * Log.info("Scrolling Down " + object);
	 * driver.get("http://www.joecolantonio.com/SeleniumTestPage.html");
	 * WebElement printButton = driver.findElement(By.id("printButton"));
	 * printButton.click(); AutoItX x = new AutoItX(); x.winActivate("Print");
	 * x.winWaitActive("Print"); x.controlClick("Print", "", "1058");
	 * x.ControlSetText("Print", "", "1153", "50"); Thread.sleep(3000); //This
	 * was added just so you could see that the values did change.
	 * x.controlClick("Print", "", "2");
	 * 
	 * Thread.sleep(3000); DriverScript.bResult = true; DriverScript.Message =
	 * "Scroll Down " + object; }catch(Exception e){ Log.error(
	 * "Not able to Scroll Down--- " + e.getMessage()); DriverScript.Message =
	 * "Not able to Scroll Down--- " + e.getMessage(); DriverScript.bResult =
	 * false; } }
	 * 
	 */
	public static void PressEnter(String object, String data) {
		try {
			Log.info("Pressing enter key on keyboard " + object);
			try {
				Robot robot = new Robot();
				robot.delay(1000);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.delay(500);
				robot.keyRelease(KeyEvent.VK_ENTER);
			} catch (AWTException e) {
				e.printStackTrace();
			}
			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Pressing enter key on keyboard " + object;
		} catch (Exception e) {
			Log.error("Not able to press enter key--- " + e.getMessage());
			DriverScript.Message = "Not able to press enter key--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Switch to Pop up
	public static void VerifyEmailTemplate(String object, String data) {
		try {
			Log.info("Scrolling Down " + object);
			mainwindow = driver.getWindowHandle();
			driver.findElement(By.xpath(".//*[@id='sample-table-1']/tbody/tr[1]/td[7]/a[1]")).click();

			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}
			System.out.println("Title1" + driver.getTitle());

			String bodyText = driver.findElement(By.tagName("body")).getText();
			String bodyText_lower = bodyText.toLowerCase();
			String data_lower = data.toLowerCase();
			if (bodyText_lower.contains(data_lower)) {
				DriverScript.bResult = true;
			} else {

				throw new Exception();
			}

			driver.close();

			driver.switchTo().window(mainwindow);

			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Email Template Verified " + object;

		} catch (Exception e) {
			driver.close();
			driver.switchTo().window(mainwindow);
			Log.error("Not able to verify email template--- " + e.getMessage());
			DriverScript.Message = "Not able to verfiy email template--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Logging into web site (front)
	public static void SiteLogin(String UserName, String Password) {
		try {
			Log.info("Login to site");

			Thread.sleep(1000);

			driver.get(OR.getProperty("SiteLogout"));

			Thread.sleep(1000);

			driver.get(OR.getProperty("SiteLogin"));

			Thread.sleep(2000);

			driver.findElement(By.xpath(".//input[@name='_username']")).sendKeys(OR.getProperty("Email1"));

			driver.findElement(By.xpath(".//input[@name='_password']")).sendKeys(OR.getProperty("Password1"));

			driver.findElement(By.xpath(".//input[@name='_username']")).submit();

			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Login Successful";
		} catch (Exception e) {
			Log.error("Fail to login--- " + e.getMessage());
			DriverScript.Message = "Fail to login--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Logging into admin site (backend)
	public static void AdminLogin(String UserName, String Password) {
		try {
			Log.info("Login to site");

			driver.get(OR.getProperty("AdminLogout"));

			driver.findElement(By.xpath(".//*[@id='login_form']/div[1]/div/input"))
					.sendKeys(OR.getProperty("AdminUserName"));

			driver.findElement(By.xpath(".//*[@id='login_form']/div[2]/div/input"))
					.sendKeys(OR.getProperty("AdminPassword"));

			driver.findElement(By.xpath(".//*[@id='login_form']/div[4]/button")).click();

			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Login Successful";
		} catch (Exception e) {
			Log.error("Fail to login--- " + e.getMessage());
			DriverScript.Message = "Fail to login--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Switch to Pop up
	public static void SendTestResultEmail(String object, String Message) {
		try {
			Log.info("Sending test result email");

			SendMail.SendEmail("PEaaS_Mistletoe Test Result", Message);

			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Sending test result email ";
		} catch (Exception e) {
			Log.error("Fail to send test result email --- " + e.getMessage());
			DriverScript.Message = "Fail to send test result email --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}


	
	// Create New account
		public static String Newaccount(String object, String Message) { 
			//String	s1="";
			try {
				Log.info("Register new account");
				
				
			 driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("autoemail" + diceRoll + "@yopmail.com");
			    		  
				Thread.sleep(3000);
				DriverScript.bResult = true;
				DriverScript.Message = "User Register Successfully...";
			} catch (Exception e) {
				Log.error("Fail to Register--- " + e.getMessage());
				DriverScript.Message = "Fail to Register--- " + e.getMessage();
				DriverScript.bResult = false;
			}
			 return s1;
		}

	

	// Move to i-frame
	public static void moveToiframe(String object, String data) {
		try {
			// WebElement selecttext =
			// driver.findElement(By.xpath(OR.getProperty(object)));

			// Actions select = new Actions(driver);
			// select.doubleClick(selecttext).build().perform();

			// driver.switchTo().defaultContent();
			// System.out.println("object" +object);
			// int size = driver.findElements(By.tagName("iframe")).size();
			//
			//
			// System.out.println(size);
			//
			// driver.switchTo().frame(0);
			// System.out.println("Move to i-frame");

			// WebElement frameCSSPath =
			// driver.findElement(By.xpath(".//[@title='Rich Text Editor,
			// editor2']"));
			// WebElement frameXPath =
			// driver.findElement(By.xpath(".//iframe[1]"));
			// driver.findElement(By.tagName("iframe").get(0));
			driver.switchTo().defaultContent();
			// driver.findElement(By.tagName("iframe")).getClass(); // frameXPath,
																					// frameTag
			System.out.println("SIZE :" + driver.findElement(By.tagName("i")));
			// driver.switchTo().frame();
			// driver.switchTo().activeElement().findElements(By.tagName("p"));(".//*[@class='cke_editable
			// cke_editable_themed cke_contents_ltr
			// cke_show_borders']/p")).click();
			
			// driver.findElement(By.xpath(".//*[@class='cke_editable
			// cke_editable_themed cke_contents_ltr
			// cke_show_borders']/p")).sendKeys("Hello");
			Thread.sleep(2000);

			DriverScript.bResult = true;
			DriverScript.Message = "move To iframe " + object;
		} catch (Exception e) {
			Log.error("Not able to move in iframe--- " + e.getMessage());
			DriverScript.Message = "Not able to move in iframe--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Find Mail from Yopmail Inbox
	public static void findMail(String object, String data) {
		try {
			Log.info("Find AOM email " + object);

			driver.switchTo().frame("ifinbox");
			List<WebElement> Links1 = driver.switchTo().activeElement().findElements(By.tagName("div"));
			for (WebElement Link1 : Links1) {
				String linktextvalue1 = Link1.getText();
				System.out.println(linktextvalue1);
				if (linktextvalue1.contains("August of Money")) {
					Link1.click();
				}
			}
			Thread.sleep(2000);
			driver.switchTo().defaultContent();
			Thread.sleep(5000);

			// Open Email
			driver.switchTo().frame("ifmail");

			List<WebElement> findhrefs = driver.switchTo().activeElement().findElements(By.tagName("a"));
			for (WebElement Link1 : findhrefs) {
				String linktextvalue1 = Link1.getText();
				System.out.println(linktextvalue1);
				if (linktextvalue1.contains("Verify your account")) {
					Link1.click();
				} else if (linktextvalue1.contains("Reset Password")) {
					Link1.click();
				}

			}
			Thread.sleep(2000);
			driver.switchTo().defaultContent();
			Thread.sleep(15000);

			// Account Activation
			System.out.println("Moving Activation PAge.");

			for (String winHandle1 : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle1);
				Thread.sleep(3000);
			}

			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Find AOM email" + object;
		} catch (Exception e) {
			Log.error("Not able to Find AOM email -- " + e.getMessage());
			DriverScript.Message = "Not able to Find AOM email --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Open New Tab + Open Yopmail.com
	public static void openNewTab(String object, String data) {
		try {
			Log.info("Opening new tab window " + object);

			Robot robot = new Robot();
			robot.delay(1000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.delay(1000);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
			System.out.println("opened new tab");

			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
				Thread.sleep(3000);
			}

			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Opening new tab window" + object;
		} catch (Exception e) {
			Log.error("Not able to Open new tab window -- " + e.getMessage());
			DriverScript.Message = "Not able to Open new tab window --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	
	//max length attribute 
	public static void maxlengthattribute(String object, String data) {
		try {
			Log.info("To check max length attribute ");
			
		String MaxLength= driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("maxlength").toString();
		System.out.println("MaxLength :"+MaxLength);
		System.out.println("DATA :"+data);
		Boolean MaxLength2 = MaxLength.equals(data);
		if(MaxLength2==true)
		{
			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Max length is= " +MaxLength;
		}
		} catch (Exception e) {
			Log.error("Max length is not proper-- " + e.getMessage());
			DriverScript.Message = "Max length is not find--"+ data + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	//Pattern attribute 
		public static void PatternAttribute(String object, String data) {
			try {
				Log.info("To Check Pattern Attribute ");
				
			String pattern= driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("pattern").toString();
			System.out.println("pattern :"+pattern);
			System.out.println("DATA :"+data);
			Boolean Pattern2 = pattern.equals(data);
			if(Pattern2==true)
			{
				Thread.sleep(3000);
				DriverScript.bResult = true;
				DriverScript.Message = "Pattern attribute is = " +pattern;
			}
			} catch (Exception e) {
				Log.error("Pattern attribute is-- " + e.getMessage());
				DriverScript.Message = "Pattern attribute is not find--"+ data + e.getMessage();
				DriverScript.bResult = false;
			}
		}
		
		
	
	// Switch Window
	public static void switchwindow(String object, String data) {
		try {
			Log.info("Switching Window " + object);
			String winHandleBefore = driver.getWindowHandle();

			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}

			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Switching New Window " + object;
		} catch (Exception e) {
			Log.error("Not able to Switching Window-- " + e.getMessage());
			DriverScript.Message = "Not able to Switching Window --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Press 3 times tab Key and press space bar
	public static void threeTimeTabPressandPressSpaceKey(String object, String data) {
		try {
			Log.info("Press Tab 3 times and Space Key" + object);

			Actions action = new Actions(driver);
			action.sendKeys(Keys.TAB).build().perform();
			action.sendKeys(Keys.TAB).build().perform();
			action.sendKeys(Keys.TAB).build().perform();
			Thread.sleep(3000);
			System.out.println("Press Tab key");
			action.sendKeys(Keys.SPACE).build().perform();
			Thread.sleep(3000);

			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
				Thread.sleep(3000);
			}

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(3000);

			StringSelection stringSelection_filepath = new StringSelection(data);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection_filepath, null);
			Thread.sleep(500);
			System.out.println("File path" + data);

			Robot r = new Robot();
			// Press CTRL+V
			r.setAutoDelay(100);
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_V);
			// Release CTRL+V
			r.keyRelease(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_CONTROL);

			r.setAutoDelay(100);
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);

			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Opening new tab window" + object;
		} catch (Exception e) {
			Log.error("Not able to Open new tab window -- " + e.getMessage());
			DriverScript.Message = "Not able to Open new tab window --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Click on Dial chapter
	public static void chpaterDialClick(String object, String data) {
		try {
			Log.info("To Click on dial of Chapter ");
			for (int i = 1; i <= 10; i++) {
				driver.findElement(By.xpath(".//*[@id='chapter-label-" + i + "']")).click();
				Thread.sleep(5000);
				driver.findElement(By.xpath(".//*[@id='home_icon_hover']/a/img")).click();
				Thread.sleep(2000);
			}

			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Dial chapter is click ";
		} catch (Exception e) {
			Log.error("Not able to click-- " + e.getMessage());
			DriverScript.Message = "Not able to click --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Press Right Arrow Key 2 times
	public static void rightArrow2Times(String object, String data) {
		try {
			Log.info("To Verify Pressing right arrow key on keyboard " + object);
			try {
				Robot robot = new Robot();
				robot.delay(1000);
				robot.keyPress(KeyEvent.VK_RIGHT);
				robot.delay(1000);
				robot.keyRelease(KeyEvent.VK_RIGHT);
				robot.delay(1000);
				/*
				 * robot.keyPress(KeyEvent.VK_RIGHT);
				 * robot.keyRelease(KeyEvent.VK_RIGHT);
				 */
			} catch (AWTException e) {
				e.printStackTrace();
			}
			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Verifying Pressing Right arrrow key on keyboard " + object;
		} catch (Exception e) {
			Log.error("Not able to press right arrow key--- " + e.getMessage());
			DriverScript.Message = "Not able to Verify press right arrow key--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// Press Left Arrow Key 2 times
	public static void leftArrow2Times(String object, String data) {
		try {
			Log.info("Pressing Left key arrow on keyboard " + object);
			try {
				Robot robot = new Robot();
				robot.delay(1000);
				robot.keyPress(KeyEvent.VK_LEFT);
				robot.delay(1000);
				robot.keyRelease(KeyEvent.VK_LEFT);
				robot.delay(1000);
				/*
				 * robot.keyPress(KeyEvent.VK_LEFT);
				 * robot.keyRelease(KeyEvent.VK_LEFT);
				 */
			} catch (AWTException e) {
				e.printStackTrace();
			}
			Thread.sleep(3000);
			DriverScript.bResult = true;
			DriverScript.Message = "Pressing Left arrow key on keyboard " + object;
		} catch (Exception e) {
			Log.error("Not able to press Left arrow key -- " + e.getMessage());
			DriverScript.Message = "Not able to press Left arrow key --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	// SelectText
	public static void SelectText(String object, String data) {
		try {
			WebElement selecttext = driver.findElement(By.xpath(OR.getProperty(object)));

			Actions select = new Actions(driver);
			select.doubleClick(selecttext).build().perform();

			Thread.sleep(2000);

			DriverScript.bResult = true;
			DriverScript.Message = "Slide " + object;
		} catch (Exception e) {
			Log.error("Not able to Select a Text--- " + e.getMessage());
			DriverScript.Message = "Not able to Select a Text--- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void rightsidenext(String object, String data) {
		try {
			Log.info("Clicking on Web element " + object);
			// driver.findElement(By.xpath(OR.getProperty(object))).click();
			WebElement slider = driver.findElement(By.xpath(OR.getProperty(object)));
			Actions move = new Actions(driver);

			for (int i = 0; i <= 5; i++) {
				// Slide to RIGHT
				slider.sendKeys(Keys.ARROW_RIGHT);
				move.click(slider).build().perform();
			}
			DriverScript.Message = "Clicking on Web element " + object;
			Thread.sleep(3000);
			DriverScript.bResult = true;
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.Message = "Not able to click --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void VerifyContentPresentByXpath(String object, String data) {
		try {
			Log.info("Verifying content is present " + object);
			System.out.println("Step Verified---->>>");
			// driver.get(OR.getProperty(object));
			if (driver.getPageSource().contains(data)) {
				DriverScript.bResult = true;
				DriverScript.Message = "Verified text " + data + " is present for " + object;
			} else {
				throw new Exception();
			}
			Thread.sleep(1500);
		} catch (Exception e) {
			Log.error("Not able to Verify Text --- " + e.getMessage());
			DriverScript.Message = "Not able to Verify Text --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

	public static void Verifylogin_ifusernotlogin(String object, String data) {
		try {
			Log.info("Verifying content is present");
			System.out.println("Step Verified---->>>");
			Boolean Xpath = driver.findElement(By.xpath("html/body/div[19]/div/div/div[2]/form[1]/div/input[3]"))
					.isDisplayed();
			if (Xpath == true) {
				driver.findElement(By.xpath(".//*[@id='login_username']")).sendKeys("cvb@yopmail.com");
				driver.findElement(By.xpath(".//*[@id='login_password']")).sendKeys("123456");
				driver.findElement(By.xpath(".//*[@id='login-button']")).click();
				DriverScript.bResult = true;
				DriverScript.Message = "Verified login";
			} else {
				driver.navigate().refresh();
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				DriverScript.bResult = true;
				DriverScript.Message = "Verified login";
			}

			Thread.sleep(1500);
		} catch (Exception e) {
			Log.error("Not able to Verify Login --- " + e.getMessage());
			DriverScript.Message = "Not able to Verify Login --- " + e.getMessage();
			DriverScript.bResult = false;
		}
	}

}
package investec.main.Web;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class main {
    private WebDriver driver;
    private WebDriverWait wait;

    private String runStatus;
    private String name;
    private String surname;
    private String email;
    private String message;

    private int _runStatus;
    private int _name;
    private int _surname;
    private int _email;
    private int _results;
    private int _comment;

    //url
    private String url = "https://www.investec.com/";

    ExcelFunctions excel;
    private String webSheet_Path;

    static ExtentReports extent = null;
    static ExtentTest test = null;
    private Screen sikuliScreen = new Screen(0);
    private String capture = "";

    @BeforeTest
    public void setUp() throws IOException {

        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\java\\investec\\dataSheet\\chromedriver.exe");  //for chrome
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        //open URL
        driver.get(url);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Path for excel sheet
        webSheet_Path = System.getProperty("user.dir") + "\\src\\main\\java\\investec\\dataSheet\\webAutomation\\webAssessment.xlsx";

        ReportFolder.ReportDirectory("Web Report");

        extent = new ExtentReports(ReportFolder.fullReportPath, true);
        //Enter path for excel sheet
        ExcelFunctions.input_document = new FileInputStream(String.valueOf(new File(webSheet_Path)));

        excel =  new ExcelFunctions();

        //read values from excel sheet by Columns Names
        _runStatus = excel.columnsNames.indexOf("runStatus");
        _results   = excel.columnsNames.indexOf("results");
        _comment  = excel.columnsNames.indexOf("comment");
        _name = excel.columnsNames.indexOf("name");
        _surname = excel.columnsNames.indexOf("surname");
        _email = excel.columnsNames.indexOf("email");

    }



    @Test
    public void mainTest() throws InterruptedException, IOException {

        //we run for a number of run on an excell sheet
        for(int y = 1; y < ExcelFunctions.ScenarioCount; y++)
        {
            //storing values from an excel sheet
            runStatus = excel.ReadCell(y,_runStatus);
            name = excel.ReadCell(y,_name);
            surname = excel.ReadCell(y,_surname);
            email = excel.ReadCell(y,_email);

            //path to write back to an excel sheet
            ExcelFunctions.output_document = new FileOutputStream(String.valueOf(new File(webSheet_Path)));

            test = extent.startTest("Investech Assessment ", "Test Case Scenarios");
            test.assignCategory("Web Assessment");
            test.assignAuthor("AUTHOR: Karabo Serope");

            //Check the status of a row before we execute, we only execute status that are on "Run" others we skip them.
            if(runStatus.equalsIgnoreCase("run")){

                //Click search icon
                driver.findElement(By.xpath("/html/body/div[2]/header/div[2]/div[1]/div/div[2]/div[3]/a/div")).click();

                //Enter"Cash Investment link" value to search with
                driver.findElement(By.id("searchBarInput")).sendKeys("Cash Investment Rate");

                //Click on search button
                driver.findElement(By.xpath("/html/body/div[2]/header/div[4]/div/div/div/div/div/div/div[1]/form/div/div[5]/div/a/button")).click();

                //Scroll down
                JavascriptExecutor jse = (JavascriptExecutor)driver;
                jse.executeScript("window.scrollBy(0,1000)");

                //Click on Understanding cash investment interest rates link
                driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[2]/div/div/div[4]/div[3]/div[3]/div/div[2]/div/div/div/a/h4")).click();

                //Wait until an element appears
                wait = new WebDriverWait(driver, 50);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div[3]/div[7]/div[2]/div/div/div/div/div[2]/div[1]/div[2]/button")));

                //Scroll down
                jse = (JavascriptExecutor)driver;
                jse.executeScript("window.scrollBy(0,1000)");

                //Click on Sign up button
                driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[7]/div[2]/div/div/div/div/div[2]/div[1]/div[2]/button")).click();

                //Enter values to register
                driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[7]/div[2]/div/div/div/div/div[2]/div/div/div/div/div[1]/div/div/div/div/div/form/div[1]/section/fieldset[2]/div[1]/text-input/div/div[1]/input")).sendKeys(name);
                driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[7]/div[2]/div/div/div/div/div[2]/div/div/div/div/div[1]/div/div/div/div/div/form/div[1]/section/fieldset[2]/div[2]/text-input/div/div[1]/input")).sendKeys(surname);
                driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[7]/div[2]/div/div/div/div/div[2]/div/div/div/div/div[1]/div/div/div/div/div/form/div[1]/section/fieldset[2]/div[3]/text-input/div/div[1]/input")).sendKeys(email);

                //scroll down
                jse = (JavascriptExecutor)driver;
                jse.executeScript("window.scrollBy(0,300)");

                //click on sign up button
                driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[7]/div[2]/div/div/div/div/div[2]/div/div/div/div/div[1]/div/div/div/div/div/form/div[1]/section/fieldset[2]/div[4]/checkbox-input/div/div/div[1]/button")).click();

                driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[7]/div[2]/div/div/div/div/div[2]/div/div/div/div/div[1]/div/div/div/div/div/form/div[1]/section/fieldset[3]/button")).click();

                //Scroll Up
                jse = (JavascriptExecutor)driver;
                jse.executeScript("window.scrollBy(0,-250)");

                //Wait until an element appears
                wait = new WebDriverWait(driver, 50);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div[3]/div[7]/div[2]/div/div/div/div/div[2]/div/div/div/div/div[1]/div/div/div/div/div/form/div[2]/div/div")));

                //Get message after signing up
                message = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[7]/div[2]/div/div/div/div/div[2]/div/div/div/div/div[1]/div/div/div/div/div/form/div[2]/div/div")).getText().trim();

                System.out.println("Successful message: " + message);

                //Ensure that the sign up process is successful
                if(message.equalsIgnoreCase("Thank you\n" +
                        "We look forward to sharing out of the ordinary insights with you")){

                    //logging my test results
                    test.log(LogStatus.INFO, message);
                    test.log(LogStatus.PASS, "Investec Test");
                    //Write the results to excel sheet
                    excel.WriteToCell("NO RUN", "Passed", message, y, _runStatus, _results, _comment);
                    //Capture the screen-shoot of the opened page for reporting
                    capture = sikuliScreen.saveScreenCapture(ReportFolder.screenshortReportPath, "Screen");
                    String screenshotName = capture.split("\\\\")[capture.split("\\\\").length - 1];
                    test.log(LogStatus.INFO, test.addScreenCapture(ReportFolder.screenshortReportPath + File.separator + screenshotName));

                }else{

                    //logging my test results
                    test.log(LogStatus.INFO, message);
                    test.log(LogStatus.FAIL, "Investec Test");
                    //Write the results to excel sheet
                    excel.WriteToCell("NO RUN", "failed", message, y, _runStatus, _results, _comment);
                    //Capture the screen-shoot of the opened page for reporting
                    capture = sikuliScreen.saveScreenCapture(ReportFolder.screenshortReportPath, "Screen");
                    String screenshotName = capture.split("\\\\")[capture.split("\\\\").length - 1];
                    test.log(LogStatus.INFO, test.addScreenCapture(ReportFolder.screenshortReportPath + File.separator + screenshotName));
                }

            }else{
                //logging my test results
                test.log(LogStatus.INFO, "Skipped");
                test.log(LogStatus.SKIP, "Investec Test");
                //Write the results to excel sheet
                excel.WriteToCell("NO RUN", "Skipped", "Test Skipped", y, _runStatus, _results, _comment);
                //Capture the screen-shoot of the opened page for reporting
                capture = sikuliScreen.saveScreenCapture(ReportFolder.screenshortReportPath, "Screen");
                String screenshotName = capture.split("\\\\")[capture.split("\\\\").length - 1];
                test.log(LogStatus.INFO, test.addScreenCapture(ReportFolder.screenshortReportPath + File.separator + screenshotName));
            }

        }

    }

    @AfterTest
    public void afterTest(){
        extent.endTest(test);
        extent.flush();
    }
}

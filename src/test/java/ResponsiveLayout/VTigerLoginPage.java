package ResponsiveLayout;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;


public class VTigerLoginPage
{
	private WebDriver driver;
	// Paths to All GSPEC Files
	String specFilePath1 = System.getProperty("user.dir") + "\\src\\test\\galen_spec_gspec\\VTigerLoginPage.gspec";

	// Initiating Chrome and Firefox Browser with URL.
	@BeforeTest
	@Parameters("browser")
	public void base(String browser) throws InterruptedException {
		String projectPath = System.getProperty("user.dir");
		if(browser.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", projectPath+"\\Resources\\geckodriver-v0.19.0-win64\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if(browser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", projectPath+"\\Resources\\BrowserDriver\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		driver.manage().window().maximize();
		driver.get("https://demo.vtiger.com/vtigercrm/index.php");
		WaitForPageLoadMethods.waitForElementToBeVisible(driver);
	}

	// Layout Report generation for Login Page.
	@Test
	public void Login() throws IOException {
		// Create a layoutReport object
		// checkLayout function checks the layout and returns a LayoutReport object
		LayoutReport objLayoutReport = Galen.checkLayout(driver, specFilePath1, Arrays.asList("desktop_GeneralWebelementAttributes"));

		// Create a galen test info list
		List<GalenTestInfo> objGalentestsList = new LinkedList<GalenTestInfo>();

		// Create a GalenTestInfo object
		GalenTestInfo objSingleGalenTest = GalenTestInfo.fromString("LOGIN");

		// Get layoutReport and assign to test object
		objSingleGalenTest.getReport().layout(objLayoutReport, "Login Components here");

		// Add test object to the tests list
		objGalentestsList.add(objSingleGalenTest);

		// Create a htmlReportBuilder object
		HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();

		// Create a report under specified folder based on tests list
		htmlReportBuilder.build(objGalentestsList, "Reports/Login_Report");

		org.testng.Assert.assertEquals(objLayoutReport.errors(), 0, "Layout Test Failed : ");
	}
	// Quit Browser after test ends.
	@AfterTest
	public void tearDown() {
		driver.close();
	}
}
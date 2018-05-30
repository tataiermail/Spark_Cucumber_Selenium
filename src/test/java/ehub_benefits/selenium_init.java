package ehub_benefits;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

//import cts.selenium.poc.testCases;

public class selenium_init {

	public static WebDriver SelDriverInit() {
		System.setProperty("webdriver.chrome.driver", "C:/Work/chromedriver.exe");
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability("platform", "Windows XP");
		caps.setCapability("version", "66.0");
		WebDriver driver=new ChromeDriver(); // Initialize browser
		return driver;
	}

}

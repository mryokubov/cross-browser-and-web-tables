import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Created by tairovich_jr on 2022-02-03.
 */
public class CrossBrowserBasicsTest {

    private WebDriver driver;

    @Parameters("browserName")
    @BeforeMethod
    public void setUp(String browserName){

        if (browserName.equals("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }else if(browserName.equals("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
    }

    @Test
    public void webOrdersPositiveTest(){

        String url= "http://secure.smartbearsoftware.com/samples/testcomplete11/WebOrders/Login.aspx";
        driver.get(url);

        driver.findElement(By.xpath("//input[contains(@id,'MainContent_username')]")).sendKeys("Tester");
        driver.findElement(By.xpath("//input[contains(@id,'MainContent_password')]")).sendKeys("test");
        driver.findElement(By.xpath("//input[@value='Login']")).click();
        Assert.assertEquals(driver.getTitle().trim(), "Web Orders");
    }


    @Test
    public void webOrdersNegativeTest(){

        String url= "http://secure.smartbearsoftware.com/samples/testcomplete11/WebOrders/Login.aspx";
        driver.get(url);

        driver.findElement(By.xpath("//input[contains(@id,'MainContent_username')]")).sendKeys("Tester");
        driver.findElement(By.xpath("//input[contains(@id,'MainContent_password')]")).sendKeys("test123");
        driver.findElement(By.xpath("//input[@value='Login']")).click();
        Assert.assertEquals(driver.getTitle().trim(), "Web Orders Login");

        WebElement errMsg = driver.findElement(By.xpath("//span[@class='error']"));
        Assert.assertTrue(errMsg.isDisplayed(), "Error Message was not displayed with incorrect creds");

    }

}

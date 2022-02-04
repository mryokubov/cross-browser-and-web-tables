package tables;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.ExcelReader;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tairovich_jr on 2022-02-03.
 */
public class HtmlTableVerification {


    private WebDriver driver;

    @BeforeMethod
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test(enabled = false)
    public void w3SchoolsTableVerificationTest(){

        driver.get("https://www.w3schools.com/html/html_tables.asp");

        int rows = driver.findElements(By.xpath("//table[@id='customers']/tbody/tr")).size(); //excluding table headers

        int cols = driver.findElements(By.xpath("//table[@id='customers']/tbody/tr[2]/td")).size();


        for (int i = 2; i <= rows; i++) {

            for (int j = 1; j <= cols; j++) {

                WebElement cellData = driver.findElement(By.xpath("//table[@id='customers']/tbody/tr[" + i + "]/td[ " + j + "]"));

                System.out.print (cellData.getText() + " ");
            }

            System.out.println();

        }




    }

    @Test()
    public void webOrdersTableVerificationTest(){
        driver.get("http://secure.smartbearsoftware.com/samples/testcomplete11/WebOrders/Login.aspx");

        driver.findElement(By.xpath("//input[contains(@id,'MainContent_username')]")).sendKeys("Tester");
        driver.findElement(By.xpath("//input[contains(@id,'MainContent_password')]")).sendKeys("test");
        driver.findElement(By.xpath("//input[@value='Login']")).click();

        driver.findElement(By.xpath("//a[text()='View all orders']")).click();


        Object[][] customers = customers();

        for (int i = 0; i < customers.length; i++) {
            for (int j = 0; j < customers[i].length; j++) {
                System.out.println(customers[i][j]);
            }
        }


        int rows = driver.findElements(By.xpath("//table[@id='ctl00_MainContent_orderGrid']/tbody/tr")).size(); //8
        int cols = driver.findElements(By.xpath("//table[@id='ctl00_MainContent_orderGrid']/tbody/tr[1]/th")).size();

        System.out.println( "========================");
        Object[][] actualData = new Object[rows-1][1];
        for (int i = 2, k = 0; i <= rows; i++, k++) {
            Map<String,String> map = new LinkedHashMap<>();
            for (int j = 2; j <= cols-1 ; j++) {

                WebElement cellData = driver.findElement(By.xpath("//table[@id='ctl00_MainContent_orderGrid']/tbody/tr[" +i+ "]/td["+ j + "]"));
                WebElement header = driver.findElement(By.xpath("//table[@id='ctl00_MainContent_orderGrid']/tbody/tr[" + 1 + "]/th[" + j + "]"));
                map.put(header.getText(), cellData.getText());
            }
            actualData[k][0] = map;
        }

        for (int i = 0; i < actualData.length; i++) {
            for (int j = 0; j < actualData[i].length; j++) {
                System.out.println(actualData[i][j]);
            }
        }


    }


 //   @DataProvider(name = "customers")
    public Object[][] customers(){
        ExcelReader reader = new ExcelReader("src/main/resources/testData/web-orders.xlsx","customers");
        return reader.getData();
    }
}

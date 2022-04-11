package cocus.login;

import common.GenerateRandom;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import java.time.Duration;
import java.util.Random;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeTest;

//I write my below scripts to simulate E2E testing scenarios, from signup to login
public class SignUpLoginE2E {

    WebDriver driver;
    String baseurl = "https://www.phptravels.net/";
    String firstname;
    String lastname;
    String phonenumber;
    String email;
    String signupToastText;
    String signInToastText;
    String password;
    String welcomeMessage;


    @BeforeClass
    public void BeforeClass(){

        //create an object of GenerateRandom class which is created by me and contains several useful methods
        GenerateRandom generateRandom = new GenerateRandom();
        //here generated random alphanumeric value and save it in their respective variables to use it later
        firstname = generateRandom.RandomAlpha();
        lastname = generateRandom.RandomAlpha();
        password = generateRandom.RandomAlpha();

        email = firstname+"@phptravels.com";

        //this is an object of the famous Random class to generate random phone number
        Random random = new Random();
        phonenumber = String.valueOf((int) Math.round(random.nextFloat() * Math.pow(10,12)));
    }

    @Test(priority = 0)
    public void SignUp() throws InterruptedException {

        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get(baseurl+"login");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

        driver.findElement(By.cssSelector("a.theme-btn:nth-child(5)")).click();
        driver.findElement(By.name("first_name")).sendKeys(firstname);
        driver.findElement(By.name("last_name")).sendKeys(lastname);
        driver.findElement(By.name("phone")).sendKeys(phonenumber);
        driver.findElement(By.name("email")).sendKeys(email);

        //the below script is to scroll down the page
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,250)");
        Thread.sleep(500);

        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.cssSelector(".btn-lg > span:nth-child(1)")).click();
        //the below is getting the text of the toast message and save it in a variable for an assert condition
        signupToastText = driver.findElement(By.className("alert-success")).getText();
        System.out.println(signupToastText);
        //the below is to ensure that a new user have been created once validate the successfull signup
        Assert.assertEquals(signupToastText,"Signup successfull please login.");
        driver.close();
    }
    //the below test script is for an attempt to login from a user already exist
    //I used this script also as a kind of E2E testing, making sure that the created new user can login successfully
    @Test(priority = 1)
    public void SuccessfulLogin() throws InterruptedException {

        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get(baseurl+"login");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/form/div[3]/button/span[1]")).click();
        Thread.sleep(5000);
        welcomeMessage = driver.findElement(By.xpath("//*[@id=\"fadein\"]/section[1]/div/div[1]/div/div[1]/div[1]/div/div/h2")).getText();
        //using assertion from hamcrest library to match insensitive username displayed on dashboard
        assertThat(welcomeMessage, equalToIgnoringCase("Hi, "+firstname+" Welcome Back"));
        System.out.println("User Logged in Successfully");
        driver.close();
    }
    //the below test script is for an attempt to login from a user does not exist
    @Test(priority = 2)
    public void UnsuccessfulLogin() throws InterruptedException {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get(baseurl+"login");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

        driver.findElement(By.name("email")).sendKeys("user@user.com");
        driver.findElement(By.name("password")).sendKeys("demouser");
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/form/div[3]/button/span[1]")).click();
        //this is how I locate the toast message and parse it to validate later if displayed or not
        signInToastText= driver.findElement(By.className("alert-danger")).getText();
        System.out.println(signInToastText);
        //Assert function to ensure that the user does not login as expected
        Assert.assertEquals(signInToastText,"Wrong credentials. try again!");
        driver.quit();
    }
}

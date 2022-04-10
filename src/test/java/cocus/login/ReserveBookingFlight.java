package cocus.login;

import common.GenerateRandom;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.Keys;


public class ReserveBookingFlight {
    WebDriver driver;
    String personalinfofirstname;
    String personalinfolastname;
    String bookemail;
    String bookphone;
    String traveller1firstname;
    String traveller1lastname;

    @BeforeClass
    public void BeforeClass(){
        WebDriverManager.firefoxdriver().setup();

        GenerateRandom generateRandom = new GenerateRandom();
        personalinfofirstname = generateRandom.RandomAlpha();
        personalinfolastname = generateRandom.RandomAlpha();
        traveller1firstname = generateRandom.RandomAlpha();
        traveller1lastname = generateRandom.RandomAlpha();
        bookemail = personalinfofirstname+"@phptravels.com";

        Random random = new Random();
        bookphone = String.valueOf((int) Math.round(random.nextFloat() * Math.pow(10,12)));

    }

    @Test
    public void BookFlight() throws InterruptedException {

        driver = new FirefoxDriver();
        driver.get("https://www.phptravels.net/flights");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.findElement(By.id("round-trip")).click();

        //this is how I handle the Autosuggestive dropdown
        driver.findElement(By.name("from")).sendKeys("beirut");
        //I get all the elements that filtered my search criteria and add them to a list
        List<WebElement> fromcountryoptions = driver.findElements(By.className("autocomplete-location"));
        //for loop to search for my desire country to select and click on it
        for(WebElement option :fromcountryoptions)
        {
            if(option.getText().equalsIgnoreCase("Beirut, Lebanon"))
            {
                option.click();
                break;
            }
        }
        //this is how I handle the Autosuggestive dropdown
        driver.findElement(By.name("to")).sendKeys("porto");
        //I get all the elements that filtered my search criteria and add them to a list
        List<WebElement> tocountryoptions = driver.findElements(By.className("autocomplete-location"));
        //for loop to search for my desire country to select and click on it
        for(WebElement option :tocountryoptions)
        {
            if(option.getText().equalsIgnoreCase("Porto, Portugal"))
            {
                option.click();
                break;
            }
        }
        //I use this primitive way to avoid writing additional libne of codes
        driver.findElement(By.id("departure")).clear();
        driver.findElement(By.id("departure")).sendKeys("30-04-2022");
        driver.findElement(By.id("return")).clear();
        driver.findElement(By.id("return")).sendKeys("10-05-2022");
        driver.findElement(By.xpath("//*[@id=\"flights-search\"]/span[1]")).click();
        //Explixit wait until the visibility of the below clickable option
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\\\"fadein\\\"]/main/div/div[1]/div/form/div[2]/div/div/div/div[1]/div/div\" +\n" +
                "                \"/fieldset/ul/li[3]/div/label")));

        driver.findElement(By.xpath("//*[@id=\"fadein\"]/main/div/div[1]/div/form/div[2]/div/div/div/div[1]/div/div" +
                "/fieldset/ul/li[3]/div/label")).click();

        WebElement slider = driver.findElement(By.xpath("/html/body/main/div/div[1]/div/form/div[2]/div/div/div/div[2]" +
                "/div/div[1]/span/span[7]"));
        //using Actions class to drag the price range
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(slider,-200,200).release().build().perform();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"data\"]/ul/li[1]/div/form/div/div[2]/div/button")).click();

        driver.findElement(By.name("firstname")).sendKeys(personalinfofirstname);
        driver.findElement(By.name("lastname")).sendKeys(personalinfolastname);
        driver.findElement(By.name("email")).sendKeys(bookemail);
        driver.findElement(By.name("phone")).sendKeys(bookphone);
        driver.findElement(By.name("address")).sendKeys("Country, City, Street, Building");

        driver.findElement(By.className("select2-selection__rendered")).click();

        driver.findElement(By.className("select2-search__field")).sendKeys("Lebanon");
        driver.findElement(By.className("select2-search__field")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"fadein\"]/div[2]/form/section/div/div/div[1]/div[1]/div[2]/div/div/div[7]" +
                "/div/div/div/span/span[1]/span")).click();
        driver.findElement(By.className("select2-search__field")).sendKeys("Lebanon");
        driver.findElement(By.className("select2-search__field")).sendKeys(Keys.ENTER);

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,400)");
        Thread.sleep(500);

        driver.findElement(By.name("firstname_1")).sendKeys(personalinfofirstname);
        driver.findElement(By.name("lastname_1")).sendKeys(personalinfolastname);
        //here I use Select class that allows me to select an element from a deop down
        WebElement nationalityDropDown = driver.findElement(By.name("nationality_1"));
        Select nationality = new Select(nationalityDropDown);
        nationality.selectByValue("LB");
        //here I use Select class that allows me to select an element from a deop down
        WebElement monthDropDown = driver.findElement(By.name("dob_month_1"));
        Select month = new Select(monthDropDown);
        month.selectByValue("03");
        //here I use Select class that allows me to select an element from a deop down
        WebElement dayDropDown = driver.findElement(By.name("dob_day_1"));
        Select day = new Select(dayDropDown);
        day.selectByValue("2");
        //here I use Select class that allows me to select an element from a deop down
        WebElement yearDropDown = driver.findElement(By.name("dob_year_1"));
        Select year = new Select(yearDropDown);
        year.selectByValue("1982");


        js.executeScript("window.scrollBy(0,400)");
        Thread.sleep(500);

        driver.findElement(By.name("passport_1")).sendKeys("LR123456");

        WebElement passportIssueMonthDrop = driver.findElement(By.name("passport_issuance_month_1"));
        Select passportIssueMonth = new Select(passportIssueMonthDrop);
        passportIssueMonth.selectByValue("12");

        WebElement passportIssueDayDrop = driver.findElement(By.name("passport_issuance_day_1"));
        Select passportIssueDay = new Select(passportIssueDayDrop);
        passportIssueDay.selectByValue("30");

        WebElement passportIssueYearDrop = driver.findElement(By.name("passport_issuance_year_1"));
        Select passportIssueYear = new Select(passportIssueYearDrop);
        passportIssueYear.selectByValue("2020");

        WebElement passportExpiryMonthDrop = driver.findElement(By.name("passport_month_1"));
        Select passportExpiryMonth = new Select(passportExpiryMonthDrop);
        passportExpiryMonth.selectByValue("01");


        WebElement passportExpiryDayDrop = driver.findElement(By.name("passport_day_1"));
        Select passportExpiryDay = new Select(passportExpiryDayDrop);
        passportExpiryDay.selectByValue("15");

        WebElement passportExpiryYearDrop = driver.findElement(By.name("passport_year_1"));
        Select passportExpiryYear = new Select(passportExpiryYearDrop);
        passportExpiryYear.selectByValue("2030");


        js.executeScript("window.scrollBy(0,200)");
        Thread.sleep(500);









    }


}

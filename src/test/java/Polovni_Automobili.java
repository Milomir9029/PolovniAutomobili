import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Polovni_Automobili {
    String email = "seleniumilosevic+" + rndGenerator() + "@protonmail.com";
    String password = "QApraksa2022";
    private WebDriver driver;
    @BeforeTest
    public void setUp() {
        System.setProperty("web-driver.chrome.driver", "C:\\IdeaProjects\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    @BeforeMethod
    void openWebsite() {
        // Open the URL
        driver.get("https://www.polovniautomobili.com/");
    }
    @Test
    public void uiTest2(){
        //driver.findElement(By.xpath("//a[.='Postavi oglas']")).click();
        WebElement postavi_oglas = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-label='Postavi oglas']")));
        postavi_oglas.click();
        driver.findElement(By.xpath("//a[.='Registruj se']")).click();
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password_first")).sendKeys(password);
        driver.findElement(By.id("password_second")).sendKeys(password);
        driver.findElement(By.id("tos")).click();
        driver.findElement(By.id("easySaleConsent")).click();
        driver.findElement(By.id("easyBuyConsent")).click();
        driver.findElement(By.xpath("//button[.='Registruj se']")).click();
        protonLogin();
        switchingTabs();
        driver.findElement(By.id("interestedInReviewingOffer")).click();
        driver.findElement(By.name("submit_registration_survey")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.='U redu']"))).click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.id("first_name"))).sendKeys("Milomir");
        driver.findElement(By.id("last_name")).sendKeys("Milosevic");
        driver.findElement(By.id("address")).sendKeys("Sopic ");
        driver.findElement(By.id("city")).sendKeys("Lazarevac");
        driver.findElement(By.xpath("//span[.=' Odaberite okrug']")).click();
        driver.findElement(By.xpath("//label[.='Beograd (širi)']")).click();
        driver.findElement(By.id("cellphone")).sendKeys("0641234567");
        driver.findElement(By.id("submit")).click();
        WebElement successMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".uk-alert-success")));
        Assert.assertTrue(successMessage.isDisplayed());
        String dropdownDivContent = "\n" +
                "\t\t\t\t\t\t\t\t\tMOJ PROFIL ";
        String xpathExpression = "//div[contains(text(), '" + dropdownDivContent + "')]";
        WebElement dropdowntrigger = driver.findElement(By.xpath(xpathExpression));
        Actions actions = new Actions(driver);
        actions.moveToElement(dropdowntrigger).perform();

       // WebElement element = driver.findElement(By.xpath("//i[contains(@class, 'position-absolute') and contains(@class, 'uk-icon-caret-down')]"));
        //element.click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(".js-logout-link"))).click();
    }
    String rndGenerator(){
        Random rnd = new Random();
        return String.format("%06d", rnd.nextInt(999999));
    }
    void protonLogin(){
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://mail.proton.me/");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("username"))).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//button[.='Sign in']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[title='Aktivirajte Vaš nalog']"))).click();
        WebElement iframe = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-subject='Aktivirajte Vaš nalog']")));
        driver.switchTo().frame(iframe);
        WebElement link = new WebDriverWait(driver, Duration.ofSeconds(10))
         .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'aktivacija-naloga')]")));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        link.click();
        driver.switchTo().defaultContent();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.='Confirm']"))).click();
    }
    void switchingTabs(){
        Set<String> handles = driver.getWindowHandles();
        List<String> handleList = new ArrayList<>(handles);
        driver.switchTo().window(handleList.get(handleList.size() - 1));
    }

}

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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


public class PolovniAutomobiliTests {
    String email = "seleniumilosevic+" + rndGenerator() + "@protonmail.com";
    String password = "QApraksa2022";
    private WebDriver driver;
    @BeforeTest
    public void setUp() {
        System.setProperty("web-driver.chrome.driver", "C:\\IdeaProjects\\chromedriver.exe");
    }
    @BeforeMethod
    void openWebsite() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.polovniautomobili.com/");
    }
    @Test
    public void RegisterVerifyLoginLogout(){
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

        protonVerification();
        switchTabs(1);

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
        WebElement logOutElement = driver.findElement(By.cssSelector(".uk-button-dropdown")).findElement(By.cssSelector(".uk-dropdown"))
                .findElement(By.cssSelector("li:last-child"));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(logOutElement)).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(".top-menu-profile"))).findElement(By.cssSelector(".js_ga-event")).click();


        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("username_header"))).sendKeys(email);
        driver.findElement(By.id("next-step")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("password_header"))).sendKeys(password);
        driver.findElement(By.name("login")).click();
        WebElement currentEmail = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".ym-hide-content")));
        String currentEmailText = currentEmail.getText();
        org.testng.Assert.assertEquals(currentEmailText, email);

        switchTabs(2);
        deleteEmail();
    }

    @Test
    public void vehicleSearch() throws InterruptedException {
        WebElement detailedSearchButton = driver.findElement(By.name("isDetailed"));
        Actions actions = new Actions(driver);
        actions.moveToElement(detailedSearchButton);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.perform();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.name("isDetailed"))).click();
        } catch (ElementClickInterceptedException ignored){
            Thread.sleep(3000);
            driver.findElement(By.name("isDetailed")).click();
        }
        // In a small number of cases the test will fail because the element will be clicked too fast after scrolling,
        // and it will still be regarded as obscured by an ad, so i used try and catch statements to circumvent that.

        popupCheck("btn_poll_no");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.=' Marka']"))).click();
        driver.findElement(By.xpath("//label[.='Audi']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.=' Svi modeli']"))).click();
        driver.findElement(By.xpath("//*[@id=\"search-form\"]/div/form/div[1]/div[1]/div/div[2]/div/div/ul/li[8]/label")).click();
        driver.findElement(By.id("price_from")).sendKeys("5000");
        driver.findElement(By.id("price_to")).sendKeys("8000");
        driver.findElement(By.xpath("//span[.=' Karoserija']")).click();
        driver.findElement(By.xpath("//*[@id=\"search-form\"]/div/form/div[1]/div[1]/div/div[7]/div/div[1]/div/div/ul/li[1]")).click();
        driver.findElement(By.xpath("//span[.=' Vrsta goriva']")).click();
        driver.findElement(By.xpath("//label[.='Benzin']")).click();
        driver.findElement(By.xpath("//span[.=' Region']")).click();
        driver.findElement(By.xpath("//*[@id=\"search-form\"]/div/form/div[1]/div[1]/div/div[11]/div/div/ul/li[1]/ul/li[1]")).click();
        driver.findElement(By.xpath("//span[.=' Godina od']")).click();
        driver.findElement(By.xpath("//label[.='2005 god.']")).click();
        driver.findElement(By.xpath("//span[.=' do']")).click();
        driver.findElement(By.xpath("//*[@id=\"search-form\"]/div/form/div[1]/div[1]/div/div[6]/div/div[2]/div/div/ul/li[5]")).click();
        driver.findElement(By.xpath("//span[.=' Broj vrata']")).click();
        driver.findElement(By.xpath("//label[.='4/5 vrata']")).click();
        driver.findElement(By.id("submit_1")).click();


    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }



    String rndGenerator(){
        Random rnd = new Random();
        return String.format("%06d", rnd.nextInt(999999));
    }

    void protonVerification(){
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
        } catch (InterruptedException ignored) {
        }
        link.click();
        driver.switchTo().defaultContent();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.='Confirm']"))).click();
    }

    void switchTabs(int tabnum){
        Set<String> handles = driver.getWindowHandles();
        List<String> handleList = new ArrayList<>(handles);
        driver.switchTo().window(handleList.get(handleList.size() - tabnum));
    }

    void deleteEmail(){
        driver.findElement(By.cssSelector("[data-testid='toolbar:movetotrash']")).click();
        driver.findElement(By.cssSelector("[title='More']")).click();
        driver.findElement(By.cssSelector("[data-testid='navigation-link:trash']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='message-column:sender-address']")));
        driver.findElement(By.cssSelector("[data-testid='toolbar:select-all-checkbox']")).click();
        driver.findElement(By.cssSelector("[data-testid='toolbar:deletepermanently']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='permanent-delete-modal:submit']"))).click();
    }
    void popupCheck(String elementID){
        try{
        WebElement popupElement = driver.findElement(By.id(elementID));
        if (popupElement.isDisplayed())
            popupElement.click();

        } catch (NoSuchElementException ignored){
        }
    }

}

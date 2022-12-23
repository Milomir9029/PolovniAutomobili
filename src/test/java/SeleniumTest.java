import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class SeleniumTest {
    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.setProperty("web-driver.chrome.driver", "C:\\IdeaProjects\\chromedriver.exe");

        driver = new ChromeDriver();
    }
    @BeforeMethod
            public void openWebsite(){
        driver.get("https://www.saucedemo.com/");
        WebElement loginForm = driver.findElement(By.cssSelector("#login_button_container"));
        loginForm.findElement(By.cssSelector("#user-name")).sendKeys("standard_user");
        loginForm.findElement(By.cssSelector("#password")).sendKeys("secret_sauce");


        loginForm.findElement(By.cssSelector("#login-button")).click();
    }

    @Test
    public void testProductsHeaderPresence() {
        WebElement headerElement = driver.findElement(By.xpath("//span[.='Products']"));
        Assert.assertTrue(headerElement.isDisplayed());
    }

    @Test
    public void testShoppingCardPresence(){
        WebElement shoppingCart = driver.findElement(By.cssSelector(".shopping_cart_link"));
        Assert.assertTrue(shoppingCart.isDisplayed());
    }

    @Test
    public void testBurgerMenuPresence(){
        WebElement burgerMenu = driver.findElement(By.cssSelector("#react-burger-menu-btn"));
        Assert.assertTrue(burgerMenu.isDisplayed());
    }
    @Test
    public void testTwitterPresence(){
        WebElement twitterLink = driver.findElement(By.xpath("//a[.='Twitter']"));
        Assert.assertTrue(twitterLink.isDisplayed());
    }
    @Test
    public void testFacebookPresence(){
        WebElement fbLink = driver.findElement(By.xpath("//a[.='Facebook']"));
        Assert.assertTrue(fbLink.isDisplayed());
    }
    @Test
    public void testLinkedInPresence(){
        WebElement LILink = driver.findElement(By.xpath("//a[.='LinkedIn']"));
        Assert.assertTrue(LILink.isDisplayed());
    }
    @AfterMethod
    public void logout() {
        driver.findElement(By.cssSelector("#react-burger-menu-btn")).click();
        WebElement logOut = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[.='Logout']")));
        logOut.click();
    }

    @AfterTest
    public void tearDown() {
        // Close the browser
        driver.quit();
    }
}

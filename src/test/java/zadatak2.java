import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;

public class zadatak2 {
        private WebDriver driver;

    @BeforeTest
        public void setUp() {

            System.setProperty("web-driver.chrome.driver", "C:\\IdeaProjects\\chromedriver.exe");
            driver = new ChromeDriver();
        }
        @BeforeMethod
        void openWebsite() {

            driver.get("https://www.saucedemo.com/");
        }
        void logIn () {
            WebElement loginForm = driver.findElement(By.cssSelector("#login_button_container"));
            loginForm.findElement(By.cssSelector("#user-name")).sendKeys("standard_user");
            loginForm.findElement(By.cssSelector("#password")).sendKeys("secret_sauce");


            loginForm.findElement(By.cssSelector("#login-button")).click();
        }
        void openBackpack () {
            driver.findElement(By.xpath("//div[.='Sauce Labs Backpack']")).click();
        }
        void verifyInfo(){
            WebElement infoDiv = driver.findElement(By.cssSelector(".inventory_details_desc_container"));
            WebElement backpackTitle = infoDiv.findElement(By.xpath("//div[.='Sauce Labs Backpack']"));
            WebElement Description = infoDiv.findElement(By.cssSelector(".inventory_details_desc"));
            WebElement price = infoDiv.findElement(By.cssSelector(".inventory_details_price"));
            Assert.assertTrue(backpackTitle.isDisplayed());
            Assert.assertTrue(Description.isDisplayed());
            Assert.assertTrue(price.isDisplayed());
        }
        void addToCart(){
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        }
        void opencart(){
            WebElement cart = driver.findElement(By.cssSelector((".shopping_cart_link")));
            cart.click();
        }
        void checkout(){
            driver.findElement(By.id("checkout")).click();

        }
        void buyerInfo(){
            driver.findElement(By.id("first-name")).sendKeys("Milomir");
            driver.findElement(By.id("last-name")).sendKeys("Milosevic");
            driver.findElement(By.id("postal-code")).sendKeys("11550");
            driver.findElement(By.id("continue")).click();
        }
        void finish(){
            driver.findElement(By.id("finish")).click();
            WebElement thankYouMessage = driver.findElement(By.xpath("//h2[.='THANK YOU FOR YOUR ORDER']"));
            Assert.assertTrue(thankYouMessage.isDisplayed());
        }
            @Test
    public void zadatak2(){
        openWebsite();
        logIn();
        openBackpack();
        verifyInfo();
        opencart();
        checkout();
        buyerInfo();
        finish();
    }
    @AfterMethod
    public void logout() {
        driver.findElement(By.cssSelector("#react-burger-menu-btn")).click();
        WebElement logOut = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[.='Logout']")));
        logOut.click();

    }
}



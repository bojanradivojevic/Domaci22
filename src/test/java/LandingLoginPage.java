import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LandingLoginPage {
    private WebDriver driver;
    private Faker faker;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        faker = new Faker();
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get("https://vue-demo.daniel-avellaneda.com/login");
    }

    @Test
    public void test1() {
        String expectedResult = "https://vue-demo.daniel-avellaneda.com/login";
        String urlElement = driver.getCurrentUrl();
        Assert.assertEquals(urlElement,expectedResult);

        String expectedEmailType = "email";
        WebElement email = driver.findElement(By.id("email"));
        String actualEmailType = email.getAttribute("type");
        Assert.assertEquals(actualEmailType,expectedEmailType);

        String expectedPasswordType = "password";
        WebElement password = driver.findElement(By.id("password"));
        String actualPasswordType = password.getAttribute("type");
        Assert.assertEquals(actualPasswordType, expectedPasswordType);
    }

    @Test
    public void test2() {
        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[3]/span/form/div/div[3]/button"));

        email.sendKeys(faker.internet().emailAddress());
        password.sendKeys(faker.internet().password());
        loginButton.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String expectedMessage = "User does not exists";
        WebElement wrongLogin_Message = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li"));
        String actualMessage = wrongLogin_Message.getText();

        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void test3() {
        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[3]/span/form/div/div[3]/button"));

        email.sendKeys("admin@admin.com");
        password.sendKeys(faker.internet().password());
        loginButton.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String expectedMessage = "Wrong password";
        WebElement wrongLogin_Message = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li"));
        String actualMessage = wrongLogin_Message.getText();

        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @AfterClass
    public void AfterClass() {
        driver.quit();
    }
}

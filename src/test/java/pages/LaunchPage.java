package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

/**
 * Created by alfredpatric on 3/23/17.
 */
public class LaunchPage extends BasePage {

    By videoError = By.xpath("//android.widget.TextView[@text='Can't play this video.' and  @index='0']");
    By okButton = By.xpath("//android.widget.Button[@text='OK']");
    By registerButton = By.xpath("//android.widget.Button[@text='Register']");

    public LaunchPage(WebDriver driver) {
        super(driver);
    }

    public LaunchPage clickRegButton() {
        waitForVisibilityOf(registerButton);
        driver.findElement(registerButton).click();
        return new LaunchPage(driver);
    }
}

package scenarios;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.*;

import java.util.concurrent.TimeUnit;

public class AppiumTest extends AndroidSetup {

    @BeforeTest
    public void setUp() throws Exception {
        prepareAndroidForAppium();
    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void PositiveRegFlow() throws Exception {

        /** Starting the Registration flow*/
        new LaunchPage(driver).clickRegButton();
        new RegistrationTypePage(driver).clickEmailSignupButton();
        new EmailSignupPage(driver).FillSignupPage();
        new EmailSignupPage(driver).submitReg();
        new RegSucessPage(driver).checkForSuccess();

        /** Waiting for success message and killing the app to launch the main activity to verify successful LOGIN
         *  The usual method of going through the flow follows after that.
         *  I have commented this out, please choose as per standards */

//        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
//        driver.startActivity("com.holmusk.glycoleap", "com.holmusk.glycoleap.main.MainActivity", null, null);
//        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

        /** The longer way is to fill in the details of profile and going to the home page (main activity) */
        new RegSucessPage(driver).clickNextButton();
        new DiseasePickerPage(driver).clickOnPrediabetes().clickOnNext();
        new RegFlowDetailsPage(driver).fillProfileDetailsAndSubmit();

        /** Dismissing the permission popups and opening the left drawer*/
        new HomePage(driver).clickOnDeny().clickOnSure().clickOnAllow().clickOnStartAppButton();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        new HomePage(driver).clickOnLeftpanelDrawer();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        /** Verification being done for login with same credentials as registration */
        new HomePage(driver).checkForNameAndPassword();

    }

}
package scenarios;

/**
 * This is the test suit which contains all the tests that I have written for registration.
 */

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

import java.util.concurrent.TimeUnit;

public class AppiumTest extends AndroidSetup {

    @BeforeMethod
    public void setUp() throws Exception {
        prepareAndroidForAppium();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }

    /**
     * This is the postive flow of the registration and verifying if the user
     * is signed-in in the main activity.
     */

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

    /**
     * This is the negative flow of the registration
     * with the a user who is already registered.
     */
    @Test (priority = 1)
    public void SameEmailNegScenario() throws InterruptedException {

        /**
         *  Starting the same Registration process with credentials which I have already registered with,
         */
        new LaunchPage(driver).clickRegButton();
        new RegistrationTypePage(driver).clickEmailSignupButton();
        new EmailSignupPage(driver).RepeatSameEmail().submitReg();
        new RegistrationErrorPopup(driver).checkForPopup().clickCloseButton();

    }

    /**
     *  Testing the toast message when the registration fails because of no internet connection.
     */

    @Test (priority = 2)
    public void NoInternetNegScenario() throws Exception {

        /** Launching the settings activity and switching on airplane mode */

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.startActivity("com.android.settings", "com.android.settings.Settings", null, null);
        new Settings(driver).switchFlightMode();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        /** Re-launching the Main activity to continue the test */

        driver.startActivity("com.holmusk.glycoleap", "com.holmusk.glycoleap.afterSplash.AfterSplashActivity", null, null);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        new LaunchPage(driver).clickRegButton();
        new RegistrationTypePage(driver).clickEmailSignupButton();
        new EmailSignupPage(driver).RepeatSameEmail().submitReg();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        /** Verifying the toast message - No internet access */

        new EmailSignupPage(driver).verifyToastMessage();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.startActivity("com.android.settings", "com.android.settings.Settings", null, null);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        new Settings(driver).switchFlightMode();

    }

    /**
     * Verifying boundary value of the Name field, it crashes after submiting with an overloaded value
     * Assering the crash message - Negative scenario
     */

    @Test (priority = 3)
    public void NameFieldTest() throws Exception {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        /** Starting the Registration flow*/
        new LaunchPage(driver).clickRegButton();
        new RegistrationTypePage(driver).clickEmailSignupButton();
        new EmailSignupPage(driver).namefieldOverload();
        new EmailSignupPage(driver).submitReg();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        new EmailSignupPage(driver).checkForCrash();

    }

    /**
     * Verifying boundary value of the email field, it crashes after submiting with an overloaded value
     * Assering the crash message - Negative scenario
     */

    @Test (priority = 4)
    public void EmailFieldTest() throws Exception {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        /** Starting the Registration flow*/
        new LaunchPage(driver).clickRegButton();
        new RegistrationTypePage(driver).clickEmailSignupButton();
        new EmailSignupPage(driver).emailfieldOverload();
        new EmailSignupPage(driver).submitReg();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        new EmailSignupPage(driver).checkForCrash();

    }

}

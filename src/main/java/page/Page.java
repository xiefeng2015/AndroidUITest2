package page;

import android.AndroidDriverWait;
import commons.Variables;
import exception.NoSuchLocatorException;
import file.YamlParser;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import android.ExpectedConditionForAndroid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Page {
    private AndroidDriver androidDriver;
    private String confYamlFile = null,locatorYamlFile = null;
    private Map locatorMap = null;


    public Page(String file) throws IOException{
        this.confYamlFile = file;
        this.getLocatorYamlFile(this.confYamlFile);
        this.getLocatorMap();
    }


    public WebElement getElement(String key) throws NoSuchLocatorException {
        return getLocator(key,true);

    }

    public WebElement getElementNoWait(String key) throws NoSuchLocatorException{
        return getLocator(key,false);
    }

    private static By getBy(String type, String value) {
        By by = null;
        if (type.equalsIgnoreCase("id")) {
            by = By.id(value);
        }
        if (type.equalsIgnoreCase("name")) {
            by = By.name(value);
        }
        if (type.equalsIgnoreCase("xpath")) {
            by = By.xpath(value);
        }
        if (type.equalsIgnoreCase("className")) {
            by = By.className(value);
        }
        if (type.equalsIgnoreCase("linkText")) {
            by = By.linkText(value);
        }
        // TODO: Check BY object for mobile
        // For mobileBy, Appium
        if (type.equalsIgnoreCase("AccessibilityId")) {
            by = MobileBy.AccessibilityId(value);
        }
        if (type.equalsIgnoreCase("AndroidUIAutomator")) {
            by = MobileBy.AndroidUIAutomator(value);
        }
        if (type.equalsIgnoreCase("IosUIAutomation")) {
            by = MobileBy.IosUIAutomation(value);
        }

        return by;
    }

    public WebElement getLocator(String key, boolean wait) throws NoSuchLocatorException {
        WebElement element = null;

        if (this.locatorMap.containsKey(key)) {
            Map map = (HashMap) locatorMap.get(key);

            String type = map.get("type").toString();
            String value = map.get("value").toString();

            By by = this.getBy(type, value);

            if (wait){
                element = waitForLocator(by);
                if (!waitLocatorToBeDisplayed(element))
                    element = null;
            }else {
                element = androidDriver.findElement(by);
            }

        }else {
            throw  new NoSuchLocatorException(
                    "\n****************************************" +
                            "****************************************\n" +
                            "Locator: [" + key + "]\n" +
                            "Locator file path: [" + this.locatorYamlFile + "]\n" +
                            "Reason: Locator is not exist in locator file." +
                            "\n****************************************" +
                            "****************************************\n"
            );
        }
        return element;
    }



    private boolean waitLocatorToBeDisplayed(final WebElement element) {
        boolean wait = false;
        if (element == null){
            return false;
        }
        try {
            wait = new AndroidDriverWait(this.androidDriver,Variables.DEFAULT_WAIT_TIME_IN_MILLIS/1000)
                    .until(new ExpectedConditionForAndroid<Boolean>() {
                        public Boolean apply(AndroidDriver androidDriver) {
                            return element.isDisplayed();
                        }
                    });
        }catch (Exception e){
            System.out.println(
                    "\n****************************************" +
                            "****************************************\n" +
                            "Locator [" +
                            element.toString() +
                            "] was not displayed in " +
                            Variables.DEFAULT_WAIT_TIME_IN_MILLIS +
                            " milliseconds." +
                            "\n****************************************" +
                            "****************************************\n"
            );
        }

           return wait;

    }

    private boolean waitLocatorToBeNotDisplayed(final  WebElement element){
        boolean wait = false;

        if(element == null){
            return false;
        }

        try {
            wait = new AndroidDriverWait(this.androidDriver,Variables.DEFAULT_WAIT_TIME_IN_MILLIS/1000)
                    .until(new ExpectedConditionForAndroid<Boolean>() {
                        public Boolean apply(AndroidDriver androidDriver) {
                            return !element.isDisplayed();
                        }
                    });
        }catch (Exception e){
            System.out.println(
                    "\n****************************************" +
                            "****************************************\n" +
                            "Locator [" +
                            element.toString() +
                            "] was displayed in " +
                            Variables.DEFAULT_WAIT_TIME_IN_MILLIS +
                            " milliseconds." +
                            "\n****************************************" +
                            "****************************************\n"
            );
        }

        return wait;
    }




    private WebElement waitForLocator(final By by) {
        WebElement element = null;
        try {
            element = new AndroidDriverWait(this.androidDriver, Variables.DEFAULT_WAIT_TIME_IN_MILLIS/1000)
                    .until(new ExpectedConditionForAndroid <WebElement>() {
                        public WebElement apply(AndroidDriver androidDriver) {
                            return androidDriver.findElement(by);

                        }

                    });
        } catch (Exception e) {
            System.out.println(
                    "\n****************************************" +
                            "****************************************\n" +
                            "Locator [" +
                            by.toString() +
                            "] was not exist until " +
                            Variables.DEFAULT_WAIT_TIME_IN_MILLIS +
                            " milliseconds." +
                            "\n****************************************" +
                            "****************************************\n"
            );
        }

        return element;

    }


    private void getLocatorYamlFile(String file) throws IOException{
        Map m = YamlParser.toMap(file);
        // Get locator yaml file path from configuration yaml file
        this.locatorYamlFile = m.get("locatorYaml").toString();
    }

    private void getLocatorMap() throws IOException{
        this.locatorMap = YamlParser.toMap(this.locatorYamlFile);
    }


    private void getWaitTime() throws IOException {
        if (YamlParser.toMap(this.confYamlFile).containsKey("waitingTime"))
            Variables.DEFAULT_WAIT_TIME_IN_MILLIS =
                    Integer.valueOf(YamlParser.toMap(this.confYamlFile).get("waitingTime").toString());
    }


//    public void main(String[] args) throws NoSuchLocatorException, IOException {
//
//        System.out.println(this.getElement("myButton"));
//    }
}
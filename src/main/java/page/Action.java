package page;

import org.openqa.selenium.WebElement;

public class Action {
    public static void type(WebElement el, String text) {
        el.sendKeys(text);
    }

    public static void clearAndType(WebElement el, String text) {
        el.clear();
        el.sendKeys(text);
    }

    public static void typeByAppend(WebElement el, String text) {
        String oldValueString = el.getText();
        String newValueString = oldValueString + text;
        el.sendKeys(newValueString);
    }

    public static void click(WebElement el) {
        el.click();
    }

    public static void clickAndWait(WebElement el, int sleepTime) throws InterruptedException {
        el.click();
        Thread.sleep(sleepTime);
    }

    public static void clear(WebElement el) {
        el.clear();
    }
}

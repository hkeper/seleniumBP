package com.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.test.pages.BasePage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class SimpleTest {

    static WebDriver driver;
    static BasePage page;
    static WebDriverWait shortWait;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
//        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setPageLoadTimeout(Duration.ofSeconds(15));
        driver = new ChromeDriver(options);
        page = new BasePage(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterAll
    static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test() throws InterruptedException, AWTException {
        String name = "Qa test";
        Actions actions = new Actions(driver);

        driver.get(ConfProperties.getProperty("baseUrl"));
        driver.findElement(By.className("banner-image"));
        page.clickCardElements();
        assertTrue(driver.findElement(By.className("element-group")).isEnabled());
        shortWait.until(ExpectedConditions.elementToBeClickable(By.id("item-0")));
        driver.findElement(By.id("item-0")).click();
        WebElement fullName = shortWait
                .until(driver -> driver.findElement(By.id("userName")));
        fullName.sendKeys(name);
        driver.findElement(By.id("userEmail")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String text = (String) js.executeScript("return arguments[0].value", fullName);
        Assertions.assertTrue(fullName.getAttribute("value").contains(name));

//        RadioButton
        driver.findElement(By.id("item-2")).click();
        WebElement rb1 = driver.findElement(By.xpath("//label[@for='yesRadio']"));
        WebElement rb2 = driver.findElement(By.xpath("//label[@for='impressiveRadio']"));
        rb1.click();

        if(rb1.isSelected()) {
            rb2.click();
            assertTrue(rb2.isSelected());
        }

        //Dropdown
        driver.navigate().to("https://demoqa.com/select-menu");

        Select select = new Select(driver.findElement(By.id("oldSelectMenu")));
        select.selectByIndex(2);

        driver.findElement(By.id("withOptGroup")).click();
        driver.findElement(By.id("react-select-2-option-0-0")).click();
        assertTrue(
                driver.findElement(By.xpath("//div[@id='withOptGroup']//div[contains(@class,'single')]")).getText()
                        .contains("Group 1, option 1")
        );

        //DragAndDrop
        driver.navigate().to("https://demoqa.com/droppable");

        actions.dragAndDrop(driver.findElement(By.id("draggable")),
                driver.findElement(By.id("droppable"))).perform();

        actions.clickAndHold(driver.findElement(By.id("draggable")))
                .moveToElement(driver.findElement(By.id("droppable")))
                        .release()
                                .build().perform();

        assertTrue(driver.findElement(By.xpath("//div[@id='droppable']/p"))
                .getText().contains("Dropped!"));
        assertTrue(driver.findElement(By.xpath("//div[@id='droppable']"))
                .getAttribute("class").contains("ui-state-highlight"));


        //Windows Tabs
        driver.navigate().to("https://demoqa.com/browser-windows");
        String baseWindow = driver.getWindowHandle();

        driver.findElement(By.id("windowButton")).click();

        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows){
            if (!window.equals(baseWindow)) {
                driver.switchTo().window(window);
                driver.close();
            }
        }

        driver.switchTo().window(baseWindow);

        driver.findElement(By.id("tabButton")).click();
        allWindows = driver.getWindowHandles();
        for (String window : allWindows){
            driver.switchTo().window(window);
        }
        driver.switchTo().window(baseWindow);

        //Upload Download files
        File file = new File("c:/Work/1/output002.txt");

        driver.navigate().to("https://demoqa.com/upload-download");
        driver.findElement(By.id("uploadFile")).sendKeys(file.getAbsolutePath());
        assertTrue(driver.findElement(By.id("uploadedFilePath")).getText().contains("output002.txt"));

        driver.findElement(By.id("downloadButton")).click();

    }

    public void fileUpload (String path) throws AWTException {
        StringSelection strSelection = new StringSelection(path);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(strSelection, null);

        Robot robot = new Robot();

        robot.delay(300);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(200);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public void fileDownload() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

    }

    public boolean isFileDownloaded(String downloadPath, String fileName) {
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();

        for (File dirContent : dirContents) {
            if (dirContent.getName().equals(fileName)) {
                // File has been found, it can now be deleted:
//                dirContent.delete();
                return true;
            }
        }
        return false;
    }

    public void tryUploadFileWithChangeVisible() {
        JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);
        WebElement webElement = shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("input[class='uploadFile']")));
        String javaScriptCode = "arguments[0].style.height='auto'; arguments[0].style.visibility='visible';";
        javascriptExecutor.executeScript(javaScriptCode, webElement);
    }

}

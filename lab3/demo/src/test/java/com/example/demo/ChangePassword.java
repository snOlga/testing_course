package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.Duration;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChangePassword {

    WebDriver driver;
    JavascriptExecutor js;

    public void init(String driverName) {
        System.setProperty(driverName.contains("chrome") ? "webdriver.chrome.driver" : "webdriver.gecko.driver", "src/test/java/resources/" + driverName + ".exe");
        driver = driverName.contains("chrome") ? new ChromeDriver() : new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
        driver.manage().window().setSize(new Dimension(900, 1100));
    }

    private void login() throws InterruptedException {
        driver.get("https://spb.hh.ru/account/login/");
        driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/div[3]/form/div[5]/div/a"))
                .click();
        WebElement login = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[1]/div/div/div[2]/div[1]/input"));
        login.clear();
        login.sendKeys("snolgasaf@yandex.ru");
        WebElement password = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[3]/div[1]/div[1]/div[2]/div[1]/input"));
        password.clear();
        password.sendKeys("UmEckcqQ2!$UTLA");
        Thread.sleep(1000);
        WebElement button = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[8]/button"));
        button.click();
    }

    private void goToSettings() {
        WebElement accountIcon = driver
                .findElement(By.xpath(
                        "/html/body/div[5]/div/div[3]/div[1]/div/div[13]/div[1]/div/button"));
        accountIcon.click();
        WebElement settings = driver.findElement(By.xpath("/html/body/div[36]/div/div/div[2]/div[1]/a[1]"));
        settings.click();
        WebElement settingsLabel = driver
                .findElement(By.xpath("/html/body/div[5]/div/div[4]/div[1]/div/div/div/div/div[1]/h1"));
        assertNotEquals(null, settingsLabel);
        assertEquals(settingsLabel.getText(), "Настройки");
        String resultUrl = js.executeScript("return window.location.href").toString();
        assertEquals(resultUrl.substring(0, resultUrl.indexOf('?')), "https://spb.hh.ru/applicant/settings");
    }

    @Test
    @ParameterizedTest
    @CsvSource({
            "chromedriver",
            "geckodriver"
    })
    void goToSettingsTest(String driverName) throws InterruptedException {
        init(driverName);
        login();
        goToSettings();
    }

    @Test
    @ParameterizedTest
    @CsvSource({
            "1,chromedriver",
            "1,geckodriver",
            "a,chromedriver",
            "a,geckodriver",
            "@,chromedriver",
            "@,geckodriver",
            "1a@,chromedriver",
            "1a@,geckodriver",
            "aaaaa,chromedriver",
            "aaaaa,geckodriver",
            "11111,chromedriver",
            "11111,geckodriver",
            "@@@@@,chromedriver",
            "@@@@@,geckodriver"
    })
    void changePasswordWrong(String newPasswordLine, String driverName) throws InterruptedException {
        init(driverName);
        login();
        goToSettings();

        WebElement changeButton = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[4]/div[1]/div/div/div/div/div[3]/div/div[1]/div[1]/div/div[2]/div/div/div[2]/a"));
        changeButton.click();
        WebElement oldPasswordField = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[4]/div[1]/div/div/div/div/div[3]/div/div[1]/div[2]/form/div[1]/div[2]/div[1]/div/div/input"));
        oldPasswordField.sendKeys("UmEckcqQ2!$UTLA");
        WebElement newPassword = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[4]/div[1]/div/div/div/div/div[3]/div/div[1]/div[2]/form/div[2]/div[3]/div/div/div/input"));
        newPassword.sendKeys(newPasswordLine);
        WebElement newPasswordAgain = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[4]/div[1]/div/div/div/div/div[3]/div/div[1]/div[2]/form/div[3]/div[3]/div/div/div/input"));
        newPasswordAgain.sendKeys(newPasswordLine);
        Thread.sleep(1000);
        WebElement save = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[4]/div[1]/div/div/div/div/div[3]/div/div[1]/div[2]/form/div[5]/div[2]/div/input"));
        save.click();
        WebElement errorMessage = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[4]/div[1]/div/div/div/div/div[3]/div/div[1]/div[2]/form/div[2]/div[3]/div/div/div/div[2]"));
        assertNotEquals(null, errorMessage);
    }

    @Test
    @Order(1)
    @ParameterizedTest
    @CsvSource({
            "chromedriver",
            "geckodriver"
    })
    void changePasswordRight(String driverName) throws InterruptedException {
        init(driverName);
        login();
        goToSettings();

        WebElement changeButton = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[4]/div[1]/div/div/div/div/div[3]/div/div[1]/div[1]/div/div[2]/div/div/div[2]/a"));
        changeButton.click();
        WebElement oldPasswordField = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[4]/div[1]/div/div/div/div/div[3]/div/div[1]/div[2]/form/div[1]/div[2]/div[1]/div/div/input"));
        oldPasswordField.sendKeys("UmEckcqQ2!$UTLA");
        WebElement newPassword = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[4]/div[1]/div/div/div/div/div[3]/div/div[1]/div[2]/form/div[2]/div[3]/div/div/div/input"));
        newPassword.sendKeys("newPasswordLine");
        WebElement newPasswordAgain = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[4]/div[1]/div/div/div/div/div[3]/div/div[1]/div[2]/form/div[3]/div[3]/div/div/div/input"));
        newPasswordAgain.sendKeys("newPasswordLine");
        Thread.sleep(1000);
        WebElement save = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[4]/div[1]/div/div/div/div/div[3]/div/div[1]/div[2]/form/div[5]/div[2]/div/input"));
        save.click();
        WebElement successfulMessage = driver.findElement(By.xpath(
                "/html/body/div[8]/div/div/div"));
        assertNotEquals(null, successfulMessage);
    }

    @Test
    @Order(2)
    @ParameterizedTest
    @CsvSource({
            "chromedriver",
            "geckodriver"
    })
    void checkNewPassword(String driverName) throws InterruptedException {
        init(driverName);
        driver.get("https://spb.hh.ru/account/login/");
        driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/div[3]/form/div[5]/div/a"))
                .click();
        WebElement login = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[1]/div/div/div[2]/div[1]/input"));
        login.clear();
        login.sendKeys("snolgasaf@yandex.ru");
        WebElement password = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[3]/div[1]/div[1]/div[2]/div[1]/input"));
        password.clear();
        password.sendKeys("newPasswordLine");
        WebElement button = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[8]/button"));
        button.click();
        Thread.sleep(1000);
        String result = js.executeScript("return window.location.href").toString();
        assertEquals(result, "https://spb.hh.ru/?hhtmFrom=account_login");
    }

}

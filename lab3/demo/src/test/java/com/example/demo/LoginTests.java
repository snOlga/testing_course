package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.Duration;

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
public class LoginTests {

    WebDriver driver;
    JavascriptExecutor js;

    public void init(String driverName) {
        System.setProperty(driverName.contains("chrome") ? "webdriver.chrome.driver" : "webdriver.gecko.driver", "src/test/java/resources/" + driverName + ".exe");
        driver = driverName.contains("chrome") ? new ChromeDriver() : new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
        driver.manage().window().setSize(new Dimension(900, 1100));
    }

    @Test
    @ParameterizedTest
    @CsvSource({
            "chromedriver",
            "geckodriver"
    })
    void redirect(String driverName) {
        init(driverName);
        driver.get("https://spb.hh.ru/");
        driver.findElement(By.xpath("/html/body/div[5]/div/div[3]/div/div/div/div/div[5]/a")).click();
        String result = js.executeScript("return window.location.href").toString();
        assertEquals(result.substring(0, result.indexOf('?')), "https://spb.hh.ru/account/login");
    }

    @Test
    @ParameterizedTest
    @CsvSource({
            "chromedriver",
            "geckodriver"
    })
    void getFieldForPassword(String driverName) {
        init(driverName);
        driver.get("https://spb.hh.ru/account/login/");
        driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/div[3]/form/div[5]/div/a"))
                .click();
        WebElement result = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[3]"));
        assertNotEquals(result, null);
    }

    @Test
    @ParameterizedTest
    @CsvSource({
            "chromedriver",
            "geckodriver"
    })
    void loginSystem(String driverName) throws InterruptedException {
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
        password.sendKeys("UmEckcqQ2!$UTLA");
        WebElement button = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[8]/button"));
        button.click();
        Thread.sleep(1000);
        String result = js.executeScript("return window.location.href").toString();
        assertEquals(result, "https://spb.hh.ru/?hhtmFrom=account_login");
    }

    @Test
    @ParameterizedTest
    @CsvSource({
            "chromedriver",
            "geckodriver"
    })
    void loginNoOpen(String driverName) {
        init(driverName);
        driver.get("https://spb.hh.ru/account/login/");
        WebElement login = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/div[3]/form/div[1]/div/div/div[2]/div[1]/input"));
        login.clear();
        login.sendKeys("snolgasaf@yandex.ru");
        driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/div[3]/form/div[5]/div/a"))
                .click();
        WebElement password = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[3]/div[1]/div[1]/div[2]/div[1]/input"));
        password.clear();
        password.sendKeys("UmEckcqQ2!$UTLA");
        WebElement button = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[8]/button"));
        button.click();
        String result = js.executeScript("return window.location.href").toString();
        assertEquals(result, "https://spb.hh.ru/?hhtmFrom=account_login");
    }

    @Test
    @ParameterizedTest
    @CsvSource({
            "chromedriver",
            "geckodriver"
    })
    void loginSystemWrongEmail(String driverName) {
        init(driverName);
        driver.get("https://spb.hh.ru/account/login/");
        String urlBefore = js.executeScript("return window.location.href").toString();
        WebElement login = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/div[3]/form/div[1]/div/div/div[2]/div[1]/input"));
        login.clear();
        login.sendKeys("snolgasafa@yandex.ru");
        driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/div[3]/form/div[5]/div/a"))
                .click();
        WebElement password = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[3]/div[1]/div[1]/div[2]/div[1]/input"));
        password.clear();
        password.sendKeys("UmEckcqQ2!$UTLA");
        WebElement button = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[8]/button"));
        button.click();
        String urlAfter = js.executeScript("return window.location.href").toString();
        assertEquals(urlBefore, urlAfter);
        WebElement errorMessage = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[7]/div[1]"));
        assertNotEquals(errorMessage, null);
    }

    @Test
    @ParameterizedTest
    @CsvSource({
            "chromedriver",
            "geckodriver"
    })
    void loginSystemWrongPassword(String driverName) {
        init(driverName);
        driver.get("https://spb.hh.ru/account/login/");
        String urlBefore = js.executeScript("return window.location.href").toString();
        WebElement login = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/div[3]/form/div[1]/div/div/div[2]/div[1]/input"));
        login.clear();
        login.sendKeys("snolgasaf@yandex.ru");
        driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/div[3]/form/div[5]/div/a"))
                .click();
        WebElement password = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[3]/div[1]/div[1]/div[2]/div[1]/input"));
        password.clear();
        password.sendKeys("UmEckcqQ2!$UTLA111111");
        WebElement button = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[8]/button"));
        button.click();
        String urlAfter = js.executeScript("return window.location.href").toString();
        assertEquals(urlBefore, urlAfter);
        WebElement errorMessage = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[7]/div[1]"));
        assertNotEquals(errorMessage, null);
    }

    @Test
    @ParameterizedTest
    @CsvSource({
            "chromedriver",
            "geckodriver"
    })
    void loginSystemNotEmail(String driverName) {
        init(driverName);
        driver.get("https://spb.hh.ru/account/login/");
        String urlBefore = js.executeScript("return window.location.href").toString();
        WebElement login = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/div[3]/form/div[1]/div/div/div[2]/div[1]/input"));
        login.clear();
        login.sendKeys("snolgasaf");
        driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/div[3]/form/div[5]/div/a"))
                .click();
        WebElement password = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[3]/div[1]/div[1]/div[2]/div[1]/input"));
        password.clear();
        password.sendKeys("UmEckcqQ2!$UTLA");
        WebElement button = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[8]/button"));
        button.click();
        String urlAfter = js.executeScript("return window.location.href").toString();
        assertEquals(urlBefore, urlAfter);
        WebElement errorMessage = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[7]/div[1]"));
        assertNotEquals(errorMessage, null);
    }

    @Test
    @ParameterizedTest
    @CsvSource({
            "chromedriver",
            "geckodriver"
    })
    void loginSystemNotEmail2(String driverName) {
        init(driverName);
        driver.get("https://spb.hh.ru/account/login/");
        String urlBefore = js.executeScript("return window.location.href").toString();
        WebElement login = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/div[3]/form/div[1]/div/div/div[2]/div[1]/input"));
        login.clear();
        login.sendKeys("snolgasaf");
        driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/div[3]/form/div[5]/div/a"))
                .click();
        WebElement button = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[8]/button"));
        button.click();
        String urlAfter = js.executeScript("return window.location.href").toString();
        assertEquals(urlBefore, urlAfter);
        WebElement errorMessage = driver.findElement(By.xpath(
                "/html/body/div[5]/div/div[1]/div[4]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/form/div[7]/div[1]"));
        assertNotEquals(errorMessage, null);
    }
}

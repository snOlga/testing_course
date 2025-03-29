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
public class SearchHelp {

    WebDriver driver;
    JavascriptExecutor js;

    public void init(String driverName) {
        System.setProperty(driverName.contains("chrome") ? "webdriver.chrome.driver" : "webdriver.gecko.driver",
                "src/test/java/resources/" + driverName + ".exe");
        driver = driverName.contains("chrome") ? new ChromeDriver() : new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
        driver.manage().window().setSize(new Dimension(900, 1100));
    }

    void openHelpVariants() {
        driver.findElement(By.xpath("/html/body/div[5]/div/div[3]/div/div/div/div/div[1]/button")).click();
        WebElement modal = driver.findElement(By.xpath("/html/body/div[12]/div/div[2]/div[2]/div[1]"));
        assertNotEquals(modal, null);
    }

    @Test
    @ParameterizedTest
    @CsvSource({
            "chromedriver",
            "geckodriver"
    })
    void testOpenHelpVariants(String driverName) {
        init(driverName);
        driver.get("https://spb.hh.ru/");
        openHelpVariants();
    }

    void openChat() {
        driver.findElement(By.xpath("/html/body/div[12]/div/div[2]/div[2]/div[1]/div[4]/div[1]/div[1]/div[3]/div"))
                .click();
        WebElement chatModal = driver.findElement(By.xpath("/html/body/div[15]"));
        assertNotEquals(chatModal, null);
    }

    @Test
    @ParameterizedTest
    @CsvSource({
            "chromedriver",
            "geckodriver"
    })
    void testOpenChat(String driverName) {
        init(driverName);
        driver.get("https://spb.hh.ru/");
        openHelpVariants();
        openChat();
    }

    @Test
    @ParameterizedTest
    @CsvSource({
            "chromedriver",
            "geckodriver"
    })
    void writeToChat(String driverName) throws InterruptedException {
        init(driverName);
        driver.get("https://spb.hh.ru/");
        openHelpVariants();
        openChat();

        driver.findElement(By.xpath("/html/body/div[15]")).click();
        WebElement input = driver.findElement(
                By.xpath("/html/body/div[1]/div[1]/div/div/div/div/div/div[3]/div/div[2]/div[2]/textarea"));
        input.sendKeys("hi");
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/div/div/div/div[3]/div/div[2]/div[3]/div/button"))
                .click();
        String resultUrl = js.executeScript("return window.location.href").toString();
        assertEquals(resultUrl.substring(0, resultUrl.indexOf('?')), "https://spb.hh.ru/");
        Thread.sleep(100);
        WebElement messageFromBot = driver.findElement(By.xpath(
                "/html/body/div[1]/div[1]/div/div/div/div/div/div[2]/div[1]/div/div[3]/div[2]/div[1]/div[2]/div"));
        assertNotEquals(messageFromBot, null);
        WebElement myMessage = driver.findElement(By.xpath(
                "/html/body/div[1]/div[1]/div/div/div/div/div/div[2]/div[1]/div/div[2]/div[2]/div[2]/div[1]"));
        assertNotEquals(myMessage, null);
    }

    void openQnA() {
        driver.findElement(By.xpath("/html/body/div[12]/div/div[2]/div[2]/div[1]/div[4]/div[1]/div[1]/div[1]/a"))
                .click();
        String resultUrl = js.executeScript("return window.location.href").toString();
        assertEquals(resultUrl.substring(0, resultUrl.indexOf('?')), "https://feedback.hh.ru/");
    }

    @Test
    @ParameterizedTest
    @CsvSource({
            "chromedriver",
            "geckodriver"
    })
    void testOpenQnA(String driverName) {
        init(driverName);
        driver.get("https://spb.hh.ru/");
        openHelpVariants();
        openQnA();
    }
}

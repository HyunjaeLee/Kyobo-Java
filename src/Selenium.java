import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * Created by Hyunjae on 9/12/16.
 */

public class Selenium implements Job {

    private static final String ID = Main.ACCOUNT[0];
    private static final String PW = Main.ACCOUNT[1];
    private static final String BASEURL = "http://www.kyobobook.co.kr/login/login.laf?retURL=http%3A//www.kyobobook.co.kr/prom/2015/book/151116_dailyCheck.jsp";
    private static final String SUBURL = "http://www.kyobobook.co.kr/prom/2015/book/151116_dailyCheck.jsp";

    public void execute(JobExecutionContext context) throws JobExecutionException {

        System.setProperty("webdriver.gecko.driver", Main.GECKOPATH);
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        WebDriver driver = new FirefoxDriver(capabilities);

        new Handler().handle(10, new Operation() {
            @Override
            public void run() {
                ((JavascriptExecutor) driver).executeScript("window.location.href = '" + BASEURL + "'");

                WebDriverWait wait =  new WebDriverWait(driver, 10);
                WebElement btn_submit = wait.until(ExpectedConditions.elementToBeClickable(By.className("btn_submit")));

                List<WebElement> memid = driver.findElements(By.id("memid"));
                memid.forEach(i -> i.sendKeys(ID));

                List<WebElement> pw = driver.findElements(By.id("pw"));
                pw.forEach(p -> p.sendKeys(PW));

                btn_submit.click();

                wait.until(ExpectedConditions.elementToBeClickable(By.linkText("로그아웃")));
            }
        }).handle(10, new Operation() {
            @Override
            public void run() {
                WebDriverWait wait =  new WebDriverWait(driver, 10);
                WebElement daily_stamp = wait.until(ExpectedConditions.elementToBeClickable(By.className("daily_stamp")));
                daily_stamp.click();
            }

            @Override
            public void exception(TimeoutException e) {
                super.exception(e);
                ((JavascriptExecutor) driver).executeScript("window.location.href = '" + SUBURL + "'");
            }
        });

        driver.quit();

    }

}

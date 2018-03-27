package com.excilys.java.formation.TestSelenium;

import static org.junit.Assert.fail;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		// On instancie notre driver, et on configure notre temps d'attente
		driver = new ChromeDriver();
		baseUrl = "http://localhost:8080/computer-database";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testSelenium() throws Exception {
		// On se connecte au site
		driver.get(baseUrl + "/DashboardServlet/");

		// On se rend page 1
		driver.findElement(By.id("contentForm:pageText")).clear();
		driver.findElement(By.id("contentForm:pageText")).sendKeys("2");
		driver.findElement(By.id("contentForm:nextPage")).click();

		// On est page 2, on va page 3
		driver.findElement(By.id("contentForm:page3Button")).click();

		// On sélectionne notre prochaine page dans la liste
		new Select(driver.findElement(By.id("contentForm:pageList_input"))).selectByVisibleText("1");
		driver.findElement(By.id("contentForm:nextPageButton")).click();

		// On est de retour page 1, on passe en anglais
		driver.findElement(By.id("headerForm:english_button")).click();

		// Et on recommence le même enchainement
		 
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alert.getText();
		} finally {
			acceptNextAlert = true;
		}
	}
}

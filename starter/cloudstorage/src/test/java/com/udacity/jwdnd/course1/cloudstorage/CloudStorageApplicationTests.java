package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		}

	@BeforeEach
	public void beforeEach() throws InterruptedException {
		this.driver = new ChromeDriver();
		loginPage = new LoginPage(driver);
		signupPage = new SignupPage(driver);
		homePage = new HomePage(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void noAccessWithoutLogin() throws InterruptedException {
		driver.get(getHomeUrl());
		Thread.sleep(1000);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void testUserSignupLoginAndLogout() throws InterruptedException {

		signupNewUser();
		Thread.sleep(1000);
		loginUser();
		Thread.sleep(1000);
		Assertions.assertEquals("Home", driver.getTitle());

		homePage.logout();
		Thread.sleep(1000);
		driver.get(getHomeUrl());
		Thread.sleep(1000);

		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(3)
	public void testAddNewNote() throws InterruptedException {

		String noteTitle = "Test title";
		String noteDescription = "Test description";

		loginUser();
		Thread.sleep(1000);
		homePage.navigateToNotesTab();
		Thread.sleep(1000);
		homePage.openNoteModal();
		Thread.sleep(500);
		homePage.addOrUpdateNote(noteTitle, noteDescription);
		Thread.sleep(500);
		driver.get(getHomeUrl());
		homePage.navigateToNotesTab();
		Thread.sleep(500);

		WebElement titleElement = driver.findElement(By.xpath(".//*[text()='" + noteTitle + "']"));
		assertNotNull(titleElement);
		WebElement noteDescriptionElement = driver.findElement(By.xpath(".//*[text()='" + noteDescription + "']"));
		assertNotNull(noteDescriptionElement);
	}

	@Test
	@Order(4)
	public void testEditExistingNote() throws InterruptedException {

		String noteTitle = "New test title";
		String noteDescription = "New test description";

		loginUser();
		Thread.sleep(1000);
		homePage.navigateToNotesTab();
		Thread.sleep(1000);
		homePage.edit();
		Thread.sleep(1000);
		homePage.clearInputNoteModal();
		Thread.sleep(1000);

		homePage.addOrUpdateNote(noteTitle, noteDescription);
		driver.get(getHomeUrl());
		homePage.navigateToNotesTab();
		Thread.sleep(500);

		WebElement titleElement = driver.findElement(By.xpath(".//*[text()='" + noteTitle + "']"));
		assertNotNull(titleElement);
		WebElement noteDescriptionElement = driver.findElement(By.xpath(".//*[text()='" + noteDescription + "']"));
		assertNotNull(noteDescriptionElement);
	}

	@Test
	@Order(5)
	public void testDeleteExistingNote() throws InterruptedException {

		String noteTitle = "New test title";
		String noteDescription = "New test description";

		loginUser();
		Thread.sleep(1000);
		homePage.navigateToNotesTab();
		Thread.sleep(1000);
		homePage.delete();
		Thread.sleep(1000);
		driver.get(getHomeUrl());
		homePage.navigateToNotesTab();
		Thread.sleep(500);

		assertThrows(NoSuchElementException.class, () ->
			{driver.findElement(By.xpath(".//*[text()='" + noteTitle + "']"));
		});
		assertThrows(NoSuchElementException.class, () ->
			{driver.findElement(By.xpath(".//*[text()='" + noteDescription + "']"));
		});
	}

	@Test
	@Order(6)
	public void testCreateNewCredential() throws InterruptedException {

		String url = "url";
		String username = "username";
		String password = "password";

		loginUser();
		Thread.sleep(1000);
		homePage.navigateToCredentialsTab();
		Thread.sleep(1000);
		homePage.openCredentialModal();
		Thread.sleep(1000);
		homePage.addOrUpdateCredential(url, username, password);

		driver.get(getHomeUrl());
		homePage.navigateToCredentialsTab();
		Thread.sleep(500);

		WebElement titleElement = driver.findElement(By.xpath(".//*[text()='" + url + "']"));
		assertNotNull(titleElement);
		WebElement noteDescriptionElement = driver.findElement(By.xpath(".//*[text()='" + username + "']"));
		assertNotNull(noteDescriptionElement);

		assertThrows(NoSuchElementException.class, () -> {
			driver.findElement(By.xpath(".//*[text()='" + password + "']"));
		});
	}

	@Test
	@Order(7)
	public void testEditExistingCredential() throws InterruptedException {
		String url = "edited url";
		String username = "edited username";
		String password = "edited password";

		loginUser();
		Thread.sleep(1000);
		homePage.navigateToCredentialsTab();
		Thread.sleep(1000);
		homePage.edit();
		Thread.sleep(500);
		homePage.clearCredentialModal();
		Thread.sleep(500);
		homePage.addOrUpdateCredential(url, username, password);
		Thread.sleep(1000);

		driver.get(getHomeUrl());
		homePage.navigateToCredentialsTab();
		Thread.sleep(500);

		WebElement titleElement = driver.findElement(By.xpath(".//*[text()='" + url + "']"));
		assertNotNull(titleElement);
		WebElement noteDescriptionElement = driver.findElement(By.xpath(".//*[text()='" + username + "']"));
		assertNotNull(noteDescriptionElement);

		assertThrows(NoSuchElementException.class, () -> {
			driver.findElement(By.xpath(".//*[text()='" + password + "']"));
		});
	}

	@Test
	@Order(8)
	public void testDeleteExistingCredential() throws InterruptedException {
		String url = "edited url";
		String username = "edited username";
		String password = "edited password";

		loginUser();
		Thread.sleep(1000);
		homePage.navigateToCredentialsTab();
		Thread.sleep(1000);
		homePage.delete();
		Thread.sleep(1000);

		driver.get(getHomeUrl());
		homePage.navigateToCredentialsTab();
		Thread.sleep(500);

		assertThrows(NoSuchElementException.class, () -> {
			driver.findElement(By.xpath(".//*[text()='" + url + "']"));
		});

		assertThrows(NoSuchElementException.class, () -> {
			driver.findElement(By.xpath(".//*[text()='" + username + "']"));
		});

		assertThrows(NoSuchElementException.class, () -> {
			driver.findElement(By.xpath(".//*[text()='" + password + "']"));
		});
	}

	private void signupNewUser() {
		driver.get(getSignupUrl());
		signupPage.signup("firstname", "lastname", "username", "password");
	}

	private void loginUser() {
		driver.get(getLoginUrl());
		loginPage.login("username", "password");
	}

	private String getSignupUrl() {
		return getBaseUrl() + "/signup";
	}

	private String getLoginUrl() {
		return getBaseUrl() + "/login";
	}

	private String getHomeUrl() {
		return getBaseUrl() + "/home";
	}

	private String getBaseUrl() {
		return "http://localhost:" + this.port;
	}

}

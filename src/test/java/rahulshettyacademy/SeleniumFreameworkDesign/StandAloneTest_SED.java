package rahulshettyacademy.SeleniumFreameworkDesign;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

//import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest_SED {

	public static void main(String[] args) {
		//WebDriverManager.chromedriver.setup();
		//1.Create new Maven Project and all Framework dependencies
		String productName= "IPHONE 13 PRO";
		WebDriverManager.chromedriver().setup();

		WebDriver driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");
		driver.findElement(By.id("userEmail")).sendKeys("preethisai75@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Qwerty123");
		driver.findElement(By.id("login")).click();
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List <WebElement> carts=driver.findElements(By.cssSelector(".mb-3"));
		
		WebElement items=carts.stream().
			filter(item->item.findElement(By.cssSelector("b")).getText().equals("IPHONE 13 PRO"))
			.findFirst().orElse(null);
		
		items.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		
		// Implementation of explicit wait to handle application synchronously on loading
		//waiting until the toast time is loaded
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofSeconds(5));
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
		 wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-spinner-overlay")));
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

		List<WebElement>cartProducts=driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean cartMatch=cartProducts.stream().anyMatch(cp->cp.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(cartMatch);
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//driver.findElement(By.cssSelector("[placeholder='Select Country']")).sendKeys("India");
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.close();
		
	}

}

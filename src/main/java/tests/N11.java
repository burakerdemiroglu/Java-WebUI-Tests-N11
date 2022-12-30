package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class N11 {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setup() {
        driver = new FirefoxDriver();
        driver.get("https://www.n11.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void test() {

        WebElement searchBtn = driver.findElement(By.className("iconsSearch"));
        Assert.assertTrue(searchBtn.isDisplayed());

        WebElement logInButton = driver.findElement(By.className("btnSignIn"));
        logInButton.click();

        WebElement cookieAccepted = driver.findElement(By.xpath("(//span[@class='setCookieBtn pickAll'])[2]"));
        cookieAccepted.click();

        WebElement mailButton = driver.findElement(By.id("email"));
        mailButton.click();
        mailButton.sendKeys("ornekmail@gmail.com");
        WebElement password = driver.findElement(By.id("password"));
        password.click();
        password.sendKeys("orneksifre");
        WebElement girisYap = driver.findElement(By.id("loginButton"));
        girisYap.click();
        WebElement searchButton = driver.findElement(By.id("searchData"));
        searchButton.click();
        searchButton.sendKeys("samsung");

        WebElement araButton = driver.findElement(By.className("iconsSearch"));
        araButton.click();

        String searchResult = driver.findElement(By.xpath("//div[@class='resultText ']/h1")).getText();
        Assert.assertEquals("Samsung", searchResult);

        WebElement element = driver.findElement(By.xpath("//div[@class='pagination hidden']/a[@data-page=2]")); // sayfa 2 numarası butonun

        while (!element.isDisplayed()) { // 2 numaralı buton görünmediği sürece aşağıdaki while döngüsü çalışacak
            List<WebElement> elements = driver.findElements(By.cssSelector(".imgHolder.cargoCampaign")); // görünen ürün sayısını liste halinde aldık
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elements.get(elements.size() - 1)); // son ürünün olduğu yere scroll ettirdik
        }
        element.click();

        String currentPage = driver.findElement(By.id("currentPage")).getAttribute("value"); // şu an bulunan sayfa numarası
        Assert.assertEquals(currentPage, "2");

        WebElement favProduct = driver.findElement(By.className("followBtn"));
        favProduct.click();
        WebElement favButton = driver.findElement(By.className("iconFavoritesWhite"));
        favButton.click();

        WebElement favQuestioning = driver.findElement(By.xpath("//ul[@class='listItemProductList']/li[not(@class='emptyLi')]//img"));
        Assert.assertTrue(favQuestioning.isDisplayed());

        WebElement favSearch = driver.findElement(By.className("listItemTitle"));
        favSearch.click();
        WebElement deleteFav = driver.findElement(By.className("deleteProFromFavorites"));
        deleteFav.click();
        WebElement deleteOk = driver.findElement(By.xpath("//span[@class='btn btnBlack confirm']"));
        deleteOk.click();
        wait.until(ExpectedConditions.invisibilityOf(deleteOk)); // delete ok butonunun kaybolmasını bekler.

        int size = driver.findElements(By.cssSelector(".column.wishListColumn")).size(); // ürünün element içerisinde döndürür varsa sayısı 1 veya 1den büyük gelir
        Assert.assertEquals(0, size); // ben ürün kaybolduğundan 0 geleceğinden size değişkeni sıfıra eşit olduğunda test başarılı olsun dedim

    }

    @After
    public void teardown() {

        driver.close();
    }
}

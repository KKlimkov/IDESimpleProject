import org.junit.jupiter.api.*;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.*;
import java.util.regex.*;
import java.io.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
public class TestCreateProject {
    public static WindowsDriver driver1 = null;
    public static WindowsDriver driver2 = null;
    String host = System.getProperty("HostIP");

    @BeforeAll
    static void setUp()  {

        DesiredCapabilities cap1 = new DesiredCapabilities();
        cap1.setCapability("platformName", "Windows");
        cap1.setCapability("deviceName", "WindowsPC");
        cap1.setCapability("app", "C:\\Program Files\\MPSSoft\\MasterSCADA 4D 1.2\\bin\\ProjectEditor.exe");
        cap1.setCapability("ms:waitForAppLaunch", "15");
        try {
            driver1 = new WindowsDriver(new URL("http://127.0.0.1:4723"), cap1);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        DesiredCapabilities cap2 = new DesiredCapabilities();
        cap2.setCapability("platformName", "Windows");
        cap2.setCapability("deviceName", "WindowsPC");
        cap2.setCapability("app", "Root");
        try {
            driver2 = new WindowsDriver(new URL("http://127.0.0.1:4723"), cap2);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("CreateProject")
    @Test
    @Tag("CreateProject")
    @Order(1)

    public void Create() throws InterruptedException {
        Actions actionProvider = new Actions(driver2);

        //Создание проекта с новым именем
        driver2.findElementByName("Создать").click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver2.findElementByAccessibilityId("ProjectNameText").sendKeys("Тестовый проект");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver2.findElementByAccessibilityId("CreateButton").click();
        try {
            Thread.sleep(25000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("AddParamAndWindow")
    @Test
    @Tag("AddParamAndWindow")
    @Order(2)

    public void AddParamAndWindow() throws InterruptedException {
        Actions actionProvider = new Actions(driver2);

        //Находим элемент дерева Система и добавляем в него АРМ
        WebElement treeElement1 = driver2.findElementByName("'Система' (Id=3 Тип=MasterSCADA.Group.SystemRoot)");
        actionProvider.contextClick(treeElement1).perform();
        driver2.findElementByName("Добавить").click();
        driver2.findElementByName("АРМ").click();

        //Находим элемент дерева АРМ 1 и добавляем в него Параметр 1 типа BOOL и Окно 1
        WebElement treeElement2 = driver2.findElementByName("АРМ 1");
        actionProvider.contextClick(treeElement2).perform();
        driver2.findElementByName("Добавить").click();
        driver2.findElementByName("Окно").click();
        actionProvider.contextClick(treeElement2).perform();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver2.findElementByName("Добавить").click();
        driver2.findElementByName("Параметр").click();
        driver2.findElementByName("BOOL").click();
        actionProvider.sendKeys(Keys.ENTER).perform();
        Thread.sleep(100);

        //Параметр АРМа тащим на окно, чтобы на окне появился текст со связанным параметром
        WebElement treeElement3 = driver2.findElementByXPath("//*[contains(@Name, 'Система.АРМ 1.Параметры.Параметр 1')]");
        //driver2.findElementByName("Параметр 1").click();
        WebElement treeElement4 = driver2.findElementByAccessibilityId("MainTabControl");
        //actionProvider.dragAndDropBy(treeElement3,300,300).build().perform();
        actionProvider.clickAndHold(treeElement3)
                //.moveByOffset(1,1)
                .moveByOffset(1, 1)
                .moveToElement(treeElement4)
                .moveByOffset(1, 1)
                .release()
                .click()
                .perform();
        actionProvider.sendKeys(Keys.ARROW_DOWN).perform();
        actionProvider.sendKeys(Keys.ARROW_DOWN).perform();
        actionProvider.sendKeys(Keys.ARROW_DOWN).perform();
        actionProvider.sendKeys(Keys.ARROW_DOWN).perform();

        actionProvider.click(treeElement3);
    }
    @DisplayName("DragLibraryPump")
    @Test
    @Tag("DragLibraryPump")
    @Order(3)

    public void DragLibraryPump() throws InterruptedException {
        Actions actionProvider = new Actions(driver2);
        //Открытие дерева библиотек для того, что вытащить кнопку с фиксацией на окно
        WebElement treeElement5 = driver2.findElementByName("Библиотеки");
        actionProvider.doubleClick(treeElement5).perform();
        Thread.sleep(100);

        WebElement treeElement6 = driver2.findElementByXPath("//*[contains(@Name, 'Библиотеки.BaseObjects')]");
        actionProvider.doubleClick(treeElement6).perform();
        Thread.sleep(100);

        WebElement treeElement7 = driver2.findElementByXPath("//*[contains(@Name, 'Библиотеки.BaseObjects.Объекты')]");
        actionProvider.doubleClick(treeElement7).perform();
        Thread.sleep(100);

        WebElement treeElement8 = driver2.findElementByXPath("//*[contains(@Name, 'Библиотеки.BaseObjects.Объекты.Насос')]");
        WebElement treeElement9 = driver2.findElementByName("'Объекты' (Id=64 Тип=MasterSCADA.Objects)");

        //Вытаскиваем на окно кнопку с фиксацией
        //WebElement treeElement4 = driver2.findElementByAccessibilityId("MainTabControl");

        actionProvider.clickAndHold(treeElement8)
                //.moveByOffset(1,1)
                .moveByOffset(1, 1)
                .moveToElement(treeElement9)
                .moveByOffset(5, 5)
                .release()
                .click()
                .perform();


        WebElement treeElement10 = driver2.findElementByXPath("//*[contains(@Name, 'Объекты.Насос 1')]");
        WebElement treeElement11 = driver2.findElementByAccessibilityId("MainTabControl");

        actionProvider.clickAndHold(treeElement10)
                //.moveByOffset(1,1)
                .moveByOffset(1, 1)
                .moveToElement(treeElement11)
                .moveByOffset(1, 1)
                .release()
                .click()
                .perform();

        actionProvider.sendKeys(Keys.ARROW_UP).perform();
        actionProvider.sendKeys(Keys.ARROW_UP).perform();
        actionProvider.sendKeys(Keys.ARROW_UP).perform();
        actionProvider.sendKeys(Keys.ARROW_UP).perform();
        actionProvider.sendKeys(Keys.ARROW_UP).perform();
    }

    @DisplayName("LinkPumpWithParam")
    @Test
    @Tag("LinkPumpWithParam")
    @Order(4)

    public void LinkPumpWithParam() throws InterruptedException {
        Actions actionProvider = new Actions(driver2);

        WebElement treeElement12 = driver2.findElementByXPath("//*[contains(@Name, 'Объекты.Насос 1')]");
        actionProvider.doubleClick(treeElement12).perform();
        WebElement treeElement13 = driver2.findElementByName("УпрВыход");
        WebElement treeElement14 = driver2.findElementByXPath("//*[contains(@Name, 'Система.АРМ 1.Параметры.Параметр 1')]");

        actionProvider.clickAndHold(treeElement13)
                //.moveByOffset(1,1)
                .moveByOffset(1, 1)
                .moveToElement(treeElement14)
                .moveByOffset(1, 1)
                .release().click()
                .perform();
    }
/*
        WebElement treeElement24 = driver2.findElementByAccessibilityId("SimpleTreeButton");
        Boolean isPresent = driver2.findElements(By.xpath("//*[contains(@Name, 'Система.АРМ 1.Службы.Межузловая связь')]")).size() > 0;
        System.out.println(isPresent);
        if (!isPresent) treeElement24.click();
        Thread.sleep(1000);

        WebElement treeElement15 = driver2.findElementByXPath("//*[contains(@Name, 'Система.АРМ 1.Графический интерфейс.Окна.Окно 1')]");
        actionProvider.click(treeElement15).sendKeys(Keys.CONTROL,Keys.ARROW_RIGHT).perform();
        actionProvider.sendKeys(Keys.INSERT).perform();
        actionProvider.sendKeys(Keys.CONTROL,Keys.INSERT).perform();

        WebElement treeElement16 = driver2.findElementByXPath("//*[contains(@Name, 'Система.АРМ 1.Графический интерфейс.Окна.Окно 1.Схема')]");
        actionProvider.click(treeElement16).sendKeys(Keys.CONTROL,Keys.ARROW_RIGHT).perform();
        actionProvider.sendKeys(Keys.INSERT).perform();
        actionProvider.sendKeys(Keys.CONTROL,Keys.INSERT).perform();
*/

    @DisplayName("GetIdElements")
    @Test
    @Tag("GetIdElements")
    @Order(5)

    public void GetIdElements() throws InterruptedException {
        Actions actionProvider = new Actions(driver2);
        WebElement treeElement24 = driver2.findElementByAccessibilityId("SimpleTreeButton");
        Boolean isPresent = driver2.findElements(By.xpath("//*[contains(@Name, 'Система.АРМ 1.Службы.Межузловая связь')]")).size() > 0;
        System.out.println(isPresent);
        if (!isPresent) treeElement24.click();
        Thread.sleep(1000);
        WebElement treeElement1 = driver2.findElementByXPath("//*[contains(@Name, 'Система.АРМ 1.Графический интерфейс.Окна.Окно 1')]");
        actionProvider.contextClick(treeElement1).perform();
        driver2.findElementByName("Открыть в панели веток").click();
        WebElement treeElement2 = driver2.findElementByName("Схема");
        actionProvider.doubleClick(treeElement2).perform();
        WebElement treeElement17 = driver2.findElementByXPath("//*[contains(@Name, 'Система.АРМ 1.Параметры.Параметр 1')]");
        WebElement treeElement18 = driver2.findElementByXPath("//*[contains(@Name, 'Объекты.Насос 1')]");
        WebElement treeElement19 = driver2.findElementByXPath("//*[contains(@Name, 'Система.АРМ 1.Графический интерфейс.Окна.Окно 1.Схема.Насос горизонт 1')]");
        String s1 = treeElement17.getAttribute("Name");
        String s2 = treeElement18.getAttribute("Name");
        String s5 = treeElement19.getAttribute("Name");
        String s3 = null;
        String s4 = null;
        String s6 = null;

        Pattern pattern = Pattern.compile("\\d+");

        Matcher matcher = pattern.matcher(s1);
        while (matcher.find()) {
            s3 = matcher.group();        }
        System.out.println(s3);

        Matcher matcher2 = pattern.matcher(s2);
        while (matcher2.find()) {
            s4 = matcher2.group();
        }
        System.out.println(s4);

        Matcher matcher3 = pattern.matcher(s5);
        while (matcher3.find()) {
            s6 = matcher3.group();
        }
        System.out.println(s6);

        try (FileWriter writer = new FileWriter("C:\\Users\\kiril\\Desktop\\Autotests\\IDE\\Data.csv", false)) {
            // запись всей строки

            writer.write(s3);
            // запись по символам
            writer.append(',');
            writer.write(s4);
            writer.append(',');
            writer.write(s6);
            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

    @DisplayName("ChangeIpAndLaunch")
    @Test
    @Tag("ChangeIpAndLaunch")
    @Order(6)

    public void ChangeIpAndLaunch() throws InterruptedException {
        Actions actionProvider = new Actions(driver2);



        WebElement treeElement20 = driver2.findElementByXPath("//*[contains(@Name, 'Система.АРМ 1.Службы')]");
        //WebElement treeElement15 = driver2.findElementByName("Службы");
        actionProvider.doubleClick(treeElement20).perform();

        //treeElement15.click();
        WebElement treeElement21 = driver2.findElementByXPath("//*[contains(@Name, 'Система.АРМ 1.Службы.Межузловая связь')]");
        actionProvider.click(treeElement21).sendKeys(Keys.CONTROL,Keys.ARROW_RIGHT).perform();
        actionProvider.sendKeys(Keys.INSERT).perform();
        actionProvider.sendKeys(Keys.CONTROL,Keys.INSERT).perform();
        WebElement treeElement22 = driver2.findElementByXPath("//*[contains(@Name, 'Система.АРМ 1.Службы.Межузловая связь.Настройки')]");
        actionProvider.doubleClick(treeElement22).perform();
        WebElement treeElement23 = driver2.findElementByXPath("//*[contains(@Name, 'Система.АРМ 1.Службы.Межузловая связь.Настройки.IP адрес')]");
        actionProvider.doubleClick(treeElement23).perform();
        WebElement treeElement26 = driver2.findElementByName("127.0.0.1");
        actionProvider.click(treeElement26).sendKeys(host).perform();
        actionProvider.sendKeys(Keys.ENTER).perform();

        actionProvider.sendKeys(Keys.F5).perform();


        Boolean isPresent2 = driver2.findElements(By.name("Обновить исполнительную систему")).size() > 0;
        if (isPresent2)
        {   WebElement treeElement25 = driver2.findElementByName("Обновить исполнительную систему");
            treeElement25.click();
        }


    }


}
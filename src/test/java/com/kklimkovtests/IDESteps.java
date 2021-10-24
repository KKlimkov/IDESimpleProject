package com.kklimkovtests;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.awt.im.InputContext;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import io.qameta.allure.Step;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class IDESteps {

    public static WindowsDriver driver2 = null;
    public static Actions actionProvider = null;
    @Step("Запуск приложения")
    public static void LaunchAPP(String NameAPP, String Wait) throws InterruptedException {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "Windows");
        cap.setCapability("deviceName", "WindowsPC");
        cap.setCapability("app", NameAPP);
        cap.setCapability("ms:waitForAppLaunch", Wait);
        try {
            driver2 = new WindowsDriver(new URL("http://127.0.0.1:4723"), cap);
            actionProvider = new Actions(driver2);
            System.out.println("Выполнено подключение к драйверу");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Step("Проверка на существование процесса")
    public static void GetProcessExistence(String ProcessName, Boolean Ex) throws IOException {
        String err;
        String line = "";
        System.out.println("Start Search Process:  " + ProcessName);
        Process p1 = Runtime.getRuntime().exec("tasklist /FI \"IMAGENAME eq " + ProcessName + ".exe\"");
        InputStream stderr = p1.getErrorStream();
        InputStream stdout = p1.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout, "Cp866"));
        BufferedReader error = new BufferedReader(new InputStreamReader(stderr, "Cp866"));
        while ((err = error.readLine()) != null) {
            System.out.println(err);
        }
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            if (Ex) {
                assertTrue(!line.contains("Задачи, отвечающие заданным критериям, отсутствуют"));
            } else
            {assertTrue(line.contains("Задачи, отвечающие заданным критериям, отсутствуют"));};
        }
    }

    @Step("Проверка клавиатуры")
    public static void ChangeLanguageKeybord(String Lang) throws InterruptedException {
        /*InputContext context = InputContext.getInstance();
        String CurrentLang = context.getLocale().toString();
        boolean b = Lang.equals("ru") & CurrentLang.equals("en_US");
        boolean c = Lang.equals("en") & CurrentLang.equals("ru_RU");
        System.out.println(CurrentLang+" "+b+" "+c);
        if (b || c)
        {*/
        //InputContext context = InputContext.getInstance();
        //    String CurrentLang = context.getLocale().toString();
        int treeElement = driver2.findElementsByName("Индикатор ввода в области уведомлений - Русский").size();
            System.out.println(treeElement);
            if (treeElement > 0){
            String selectAll = Keys.chord(Keys.ALT, Keys.SHIFT);
            actionProvider.sendKeys(selectAll).perform();
            System.out.println("Выполнено изменение раскладки клавиатуры на" + Lang);
            }
       // }
        Thread.sleep(500);

    }

    @Step("Создание проекта")
    public static void CreateProject(String NameProject, Integer Wait) throws InterruptedException {
        driver2.findElementByName("Создать").click();
        Thread.sleep(1000);
        driver2.findElementByAccessibilityId("ProjectNameText").sendKeys(NameProject);
        Thread.sleep(1000);
        driver2.findElementByAccessibilityId("CreateButton").click();
        Thread.sleep(1000);
        actionProvider.sendKeys(Keys.ENTER).perform();
        Thread.sleep(Wait);
        System.out.println("Создан проект " + NameProject);
    }

    @Step("Создание АРМа")
    public static void AddARM() throws InterruptedException {
        WebElement treeElement = driver2.findElementByName("'Система' (Id=3 Тип=MasterSCADA.Group.SystemRoot)");
        actionProvider.contextClick(treeElement).perform();
        driver2.findElementByName("Добавить").click();
        driver2.findElementByName("АРМ").click();
        Thread.sleep(500);
    }

    @Step("Добавление окна")
    public static void AddWindow(String Where) throws InterruptedException {
        WebElement treeElement = driver2.findElementByName(Where);
        actionProvider.contextClick(treeElement).perform();
        Thread.sleep(500);
        driver2.findElementByName("Добавить").click();
        Thread.sleep(500);
        driver2.findElementByName("Окно").click();
        Thread.sleep(500);
        int Error = driver2.findElementsByClassName("ScrollViewer").size();
        if ( Error > 0)
        {
            driver2.findElementByName("OK").click();
            Thread.sleep(500);
            actionProvider.contextClick(treeElement).perform();
            Thread.sleep(500);
            driver2.findElementByName("Добавить").click();
            Thread.sleep(500);
            driver2.findElementByName("Окно").click();

        }
        else
        Thread.sleep(500);
        System.out.println("Добавлены АРМ и Окно АРМа");

    }

    @Step("Добавление параметра")
    public static void AddParam(String Where, String Type) throws InterruptedException {
        WebElement treeElement = driver2.findElementByName(Where);
        actionProvider.contextClick(treeElement).perform();
        Thread.sleep(1000);
        driver2.findElementByName("Добавить").click();
        driver2.findElementByName("Параметр").click();
        driver2.findElementByName(Type).click();
        actionProvider.sendKeys(Keys.ENTER).perform();
        Thread.sleep(100);
        System.out.println("Добавлен параметр типа " + Type + "в" + Where);
    }

    @Step("Перенос элемента")
    public static void DragAndDropElement(String WhatByDrag, String WhatDrag, String WhereByDrop, String Where) throws InterruptedException {
        WebElement treeElement3 = null;
        WebElement treeElement4 = null;

        switch (WhatByDrag) {
            case ("XPath"):
                treeElement3 = driver2.findElementByXPath(WhatDrag);
                break;
            case ("AccessibilityId"):
                treeElement3 = driver2.findElementByAccessibilityId(WhatDrag);
                break;
            case ("Name"):
                treeElement3 = driver2.findElementByName(WhatDrag);
                break;
        }

        switch (WhereByDrop) {
            case ("XPath"):
                treeElement4 = driver2.findElementByXPath(Where);
                break;
            case ("AccessibilityId"):
                treeElement4 = driver2.findElementByAccessibilityId(Where);
                break;
            case ("Name"):
                treeElement4 = driver2.findElementByName(Where);
                break;
        }
        actionProvider.clickAndHold(treeElement3)
                //.moveByOffset(1,1)
                .moveByOffset(1, 1)
                .moveToElement(treeElement4)
                .moveByOffset(1, 1)
                .release()
                .click()
                .perform();
        actionProvider.click(treeElement3);
        System.out.println("Выполнен перенос элемента");
    }

    @Step("Перенос элемента с действием")
    public static void DragAndDropElementWithAction(String WhatByDrag, String WhatDrag, String WhereByDrop, String Where, String MoveAction, Integer NumberMoveAction) throws InterruptedException {
        WebElement treeElement3 = null;
        WebElement treeElement4 = null;

        switch (WhatByDrag) {
            case ("XPath"):
                treeElement3 = driver2.findElementByXPath(WhatDrag);
                break;
            case ("AccessibilityId"):
                treeElement3 = driver2.findElementByAccessibilityId(WhatDrag);
                break;
            case ("Name"):
                treeElement3 = driver2.findElementByName(WhatDrag);
                break;
        }

        switch (WhereByDrop) {
            case ("XPath"):
                treeElement4 = driver2.findElementByXPath(Where);
                break;
            case ("AccessibilityId"):
                treeElement4 = driver2.findElementByAccessibilityId(Where);
                break;
            case ("Name"):
                treeElement4 = driver2.findElementByName(Where);
                break;
        }
            actionProvider.clickAndHold(treeElement3)
                //.moveByOffset(1,1)
                .moveByOffset(1, 1)
                .moveToElement(treeElement4)
                .moveByOffset(1, 1)
                .release()
                .click()
                .perform();

        switch (MoveAction) {
            case  ("ARROW_DOWN"):
                for (int i = 1; i < NumberMoveAction; i++) {
                    actionProvider.sendKeys(Keys.ARROW_DOWN).perform();
                }
                break;
            case ("ARROW_UP"):
                for (int i = 1; i < NumberMoveAction; i++) {
                    actionProvider.sendKeys(Keys.ARROW_UP).perform();
                }
                break;
        }
        actionProvider.click(treeElement3);
        System.out.println("Выполнен перенос элемента с действием");
    }

    @Step("Раскрытие библиотеки BaseObject")
    public static void GetLibraryObject(String LibraryName) throws InterruptedException {
        WebElement treeElement5 = driver2.findElementByName("Библиотеки");
        actionProvider.doubleClick(treeElement5).perform();
        Thread.sleep(100);
        WebElement treeElement6 = driver2.findElementByXPath("//*[contains(@Name, 'Библиотеки."+LibraryName+"')]");
        actionProvider.doubleClick(treeElement6).perform();
        Thread.sleep(100);
        WebElement treeElement7 = driver2.findElementByXPath("//*[contains(@Name, 'Библиотеки."+LibraryName+".Объекты')]");
        actionProvider.doubleClick(treeElement7).perform();
        Thread.sleep(100);
        System.out.println("Открыт библиотечный объект");
    }

    @Step("Двойнок клик по элементу дерева по Xpath")
    public static void DoubleClickTreeElement(String Xpath) throws InterruptedException {
        WebElement treeElement12 = driver2.findElementByXPath(Xpath);
        actionProvider.doubleClick(treeElement12).perform();
        Thread.sleep(1000);
        System.out.println("Выполнен двойной клик по элементу по XPath");
    }

    @Step("Двойнок клик по элементу дерева по Name")
    public static void DoubleClickTreeElementName(String Name) throws InterruptedException {
        WebElement treeElement12 = driver2.findElementByName(Name);
        actionProvider.doubleClick(treeElement12).perform();
        Thread.sleep(1000);
        System.out.println("Выполнен двойной клик по элементу по Name");
    }

    @Step("Открыть следуюший элемент дерева с помощью ctrl+arrow")
    public static void OpenNextTreeElementByArrow(String Xpath) throws InterruptedException {
        WebElement treeElement21 = driver2.findElementByXPath(Xpath);
        actionProvider.click(treeElement21).sendKeys(Keys.CONTROL,Keys.ARROW_RIGHT).perform();
        actionProvider.sendKeys(Keys.INSERT).perform();
        actionProvider.sendKeys(Keys.CONTROL,Keys.INSERT).perform();
        Thread.sleep(1000);
        System.out.println("Выполнено открытие элемента дерева с помощью ctrl+стрелочка");
    }

    @Step("Отображение полного дерева")
    public static void TreeMode(String Mode) throws InterruptedException {
        WebElement treeElement24 = driver2.findElementByAccessibilityId("SimpleTreeButton");
        String test = treeElement24.getAttribute("Toggle.ToggleState");
        System.out.println(test);
        boolean b = (Mode.equals("Full") & test.equals("0")) || (Mode.equals("Usual") & test.equals("1"));
        if (!b) treeElement24.click();
        System.out.println("Открыто полное дерево ");
        Thread.sleep(1000);
    }

    @Step("Открыть в новой панели веток дерева по Xpath")
    public static void OpenElementInNewBranch(String Xpath) throws InterruptedException {
        WebElement treeElement12 = driver2.findElementByXPath(Xpath);
        actionProvider.contextClick(treeElement12).perform();
        driver2.findElementByName("Открыть в панели веток").click();
        System.out.println("Выполнено открытие элемента в новой панели веток");
    }

    @Step("Запись в файл ID по ХPath")
    public static void WriteIdByPath(String Path, String FilePath, Boolean First, Boolean Last) throws InterruptedException {
        WebElement treeElement17 = driver2.findElementByXPath(Path);
        String s1 = treeElement17.getAttribute("Name");
        String s2 = null;

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(s1);
        while (matcher.find()) {
            s2 = matcher.group();        }
        System.out.println(s2);

        try (FileWriter writer = new FileWriter(FilePath, First)) {
            writer.write(s2);
            if (!Last) writer.append(',');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Произведена запись id в файл");
    }

    @Step("Задать свойство в диалоговом окне редактирования параметра")
    public static void SetValueInDialogWindow(String WhatChange, String ByChange) throws InterruptedException {
        ChangeLanguageKeybord("en");
        WebElement treeElement26 = driver2.findElementByName(WhatChange);
        actionProvider.click(treeElement26).sendKeys(ByChange).perform();
        actionProvider.sendKeys(Keys.ENTER).perform();
        System.out.println("Выполнено задание свойства в диалоговом окне");
    }

    @Step("Запустить RT")
    public static void RunRT() throws InterruptedException {
        actionProvider.sendKeys(Keys.F5).perform();
        System.out.println("Выполнен запуск проекта по F5");
        Thread.sleep(15000);
    }

    @Step("Обновить исполнительную систему")
    public static void SendNewRTFiles() throws InterruptedException {
        Boolean isPresent2 = driver2.findElements(By.name("Обновить исполнительную систему")).size() > 0;
        if (isPresent2)
        {   WebElement treeElement25 = driver2.findElementByName("Обновить исполнительную систему");
            treeElement25.click();
        }
        else System.out.println("Обновление не требуется");
    }

    @Step("Сохранить проект")
    public static void SaveProject() throws InterruptedException {
        String saveAll = Keys.chord(Keys.CONTROL, "s");
        actionProvider.sendKeys(saveAll).perform();
        Thread.sleep(500);
        System.out.println("Выполнено сохранение проекта");
    }

    @Step("Закрыть проект")
    public static void CloseProject() throws InterruptedException {
        WebElement treeElement25 = driver2.findElementByAccessibilityId("PART_CloseButton");
        treeElement25.click();
        Thread.sleep(2000);
        System.out.println("Выполнено закрытие проекта");
    }

    @Step("Запустить драйвер")
    public static void StartDriver() throws InterruptedException, IOException {
        String command = "C:\\Program Files (x86)\\Windows Application Driver\\WinAppDriver.exe";
        Runtime.getRuntime().exec(command);
        Thread.sleep(1000);
    }

    @Step("Остановить драйвер")
    public static void StopDriver() throws InterruptedException {
        driver2.close();
        driver2.quit();
    }

}

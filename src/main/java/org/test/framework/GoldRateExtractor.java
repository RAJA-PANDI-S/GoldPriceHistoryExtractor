package org.test.framework;

// ============================
// Required Imports
// ============================

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// ============================
// Main Class
// ============================

public class GoldRateExtractor {
    public WebDriver driver;

    public static void main(String[] args) throws Exception {

        // =====================================================
        // STEP 1: Setup Chrome Browser using WebDriverManager
        // =====================================================
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();

        //WebDriverManager.chromedriver().setup();   // Automatically manages ChromeDriver
        //WebDriver driver = new ChromeDriver();     // Launch Chrome browser

        driver.manage().window().maximize();       // Maximize browser window

        // Explicit wait for handling dynamic elements
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Open Gold Rate website
        driver.get("https://www.goodreturns.in/gold-rates/chennai.html");

        System.out.println("Step 1: Site has been opened");
        // =====================================================
        // STEP 2: Create Excel Workbook & Sheet
        // =====================================================

        XSSFWorkbook workbook = new XSSFWorkbook();          // Create Excel workbook
        XSSFSheet sheet = workbook.createSheet("Gold Rates"); // Create sheet

        // Create header row
        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Date");
        headerRow.createCell(1).setCellValue("24K Gold (₹/g)");
        headerRow.createCell(2).setCellValue("22K Gold (₹/g)");
        headerRow.createCell(3).setCellValue("18K Gold (₹/g)");

        int excelRowNumber = 1;  // Row counter for Excel

        System.out.println("Step 2: Excel has been Created");

        // =====================================================
        // STEP 3: Define Date Range (Last 30 Days Example)
        // =====================================================

        LocalDate endDate = LocalDate.now();          // Today
        LocalDate startDate = endDate.minusDays(30); // Last 30 days

        // Format required for displaying date in Excel
        DateTimeFormatter displayDateFormat =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println("Step 3: Date range defined");

        // =====================================================
        // STEP 4: Loop Through Each Date
        // =====================================================

        for (LocalDate currentDate = startDate;
             !currentDate.isAfter(endDate);
             currentDate = currentDate.plusDays(1)) {

            try {

                // =====================================================
                // STEP 4.1: Open Calendar
                // =====================================================

                WebElement calendarIcon = wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//input[@id='gold-date-picker']"))
                );

                calendarIcon.click();

                System.out.println("Step 4.1: Calendar clicked");

                // =====================================================
                // STEP 4.2: Select Date using JavaScript
                // (Used because calendar is JS-driven)
                // =====================================================
                JavascriptExecutor js = (JavascriptExecutor) driver;

                DateTimeFormatter pickerFormat =
                        DateTimeFormatter.ofPattern("yyyy-MM-dd");

                String pickerDate = currentDate.format(pickerFormat);

                js.executeScript(
                        "arguments[0].value = arguments[1];" +
                                "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                        calendarIcon,
                        pickerDate
                );

                Thread.sleep(2000);

                System.out.println("Step 4.2: Date selected -> " + pickerDate);

                // =====================================================
                // STEP 4.3: Fetch Gold Prices
                // =====================================================

                String gold24KPrice = driver.findElement(
                        By.xpath("//*[@id=\"24K-price\"]")).getText();

                String gold22KPrice = driver.findElement(
                        By.xpath("//*[@id=\"22K-price\"]")).getText();

                String gold18KPrice = driver.findElement(
                        By.xpath("//*[@id=\"18K-price\"]")).getText();

                System.out.println("Step 4.3: Gold rate fetched successfully");

                // =====================================================
                // STEP 4.4: Write Data to Excel
                // =====================================================

                XSSFRow dataRow = sheet.createRow(excelRowNumber++);

                dataRow.createCell(0)
                        .setCellValue(currentDate.format(displayDateFormat));

                dataRow.createCell(1).setCellValue(gold24KPrice);
                dataRow.createCell(2).setCellValue(gold22KPrice);
                dataRow.createCell(3).setCellValue(gold18KPrice);

                // Console log for tracking
                System.out.println("Fetched data for: "
                        + currentDate.format(displayDateFormat));

            } catch (Exception e) {

                // If any date fails, log and continue with next date
                System.out.println("Failed for date: " + currentDate
                        + " | Reason: " + e.getMessage());
            }
        }

        System.out.println("Step 4.4: Data stored on Excel");

        // =====================================================
        // STEP 5: Save Excel File
        // =====================================================

        FileOutputStream fileOutputStream =
                new FileOutputStream("Chennai_Gold_Rates.xlsx");

        workbook.write(fileOutputStream);  // Write data into Excel
        fileOutputStream.close();          // Close file stream
        workbook.close();                  // Close workbook

        System.out.println("Step 5: Excel file saved");

        // =====================================================
        // STEP 6: Close Browser
        // =====================================================

        driver.quit();

        System.out.println("Gold rate extraction completed successfully!");
    }
}

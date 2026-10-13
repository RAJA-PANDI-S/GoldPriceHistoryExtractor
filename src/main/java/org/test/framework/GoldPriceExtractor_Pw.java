package org.test.framework;

import com.microsoft.playwright.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GoldPriceExtractor_Pw {

    public static void main(String[] args) throws Exception {

        //=====================================================
        // STEP 1 : Launch Browser
        //=====================================================

        Playwright playwright = Playwright.create();

        Browser browser = playwright.firefox().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
        );

        BrowserContext context = browser.newContext();

        Page page = context.newPage();

        page.navigate("https://www.goodreturns.in/gold-rates/chennai.html");

        page.setViewportSize(1920,1080);

        System.out.println("Step 1 : Site opened successfully");

        //=====================================================
        // STEP 2 : Create Excel Workbook
        //=====================================================

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("Gold Rates");

        XSSFRow header = sheet.createRow(0);

        header.createCell(0).setCellValue("Date");
        header.createCell(1).setCellValue("24K Gold (₹/g)");
        header.createCell(2).setCellValue("22K Gold (₹/g)");
        header.createCell(3).setCellValue("18K Gold (₹/g)");

        int excelRow = 1;

        System.out.println("Step 2 : Excel Created");

        //=====================================================
        // STEP 3 : Date Range
        //=====================================================

        LocalDate endDate = LocalDate.now();

        LocalDate startDate = LocalDate.of(2026,10,1);

        DateTimeFormatter pickerFormat =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");

        DateTimeFormatter excelFormat =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        //=====================================================
        // STEP 4 : Loop Through Dates
        //=====================================================

        for(LocalDate currentDate = startDate;
            !currentDate.isAfter(endDate);
            currentDate = currentDate.plusDays(1)) {

            try {

                Locator datePicker = page.locator("#gold-date-picker");

                datePicker.waitFor();

                datePicker.click();

                String pickerDate = currentDate.format(pickerFormat);

                //=====================================================
                // Set Date using JavaScript
                //=====================================================

                page.evaluate(
                        "(args) => {" +
                                "const element = document.querySelector('#gold-date-picker');" +
                                "element.value = args;" +
                                "element.dispatchEvent(new Event('change',{bubbles:true}));" +
                                "}",
                        pickerDate
                );

                page.waitForTimeout(2000);

                System.out.println("Date Selected : " + pickerDate);

                //=====================================================
                // Read Gold Prices
                //=====================================================

                String price24K = page.locator("#24K-price").textContent().trim();

                String price22K = page.locator("#22K-price").textContent().trim();

                String price18K = page.locator("#18K-price").textContent().trim();

                //=====================================================
                // Write to Excel
                //=====================================================

                XSSFRow row = sheet.createRow(excelRow++);

                row.createCell(0)
                        .setCellValue(currentDate.format(excelFormat));

                row.createCell(1).setCellValue(price24K);
                row.createCell(2).setCellValue(price22K);
                row.createCell(3).setCellValue(price18K);

                System.out.println(
                        "Fetched : "
                                + currentDate.format(excelFormat)
                                + " | 24K : " + price24K
                                + " | 22K : " + price22K
                                + " | 18K : " + price18K
                );

            }
            catch (Exception e){

                System.out.println(
                        "Failed for "
                                + currentDate
                                + " Reason : "
                                + e.getMessage()
                );
            }

        }

        //=====================================================
        // STEP 5 : Save Excel
        //=====================================================

        FileOutputStream fos =
                new FileOutputStream("Chennai_Gold_Rates_Pw.xlsx");

        workbook.write(fos);

        fos.close();

        workbook.close();

        System.out.println("Excel Saved Successfully");

        //=====================================================
        // STEP 6 : Close Browser
        //=====================================================

        browser.close();

        playwright.close();

        System.out.println("Execution Completed Successfully");

    }

}
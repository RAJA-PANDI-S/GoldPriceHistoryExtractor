Gold Price History Project - Beginner Friendly Explanation
This is a Java web scraping project that automatically collects gold price data from a website and saves it to an Excel file.

What This Project Does
The program visits a gold price website (goodreturns.in) and:

Opens a web browser automatically
Selects different dates (last 30 days)
Extracts gold prices for different purities (24K, 22K, 18K)
Saves all the data to an Excel file
Key Components
Project Structure
pom.xml: Maven configuration file that manages project dependencies
Main.java: Basic entry point (currently just shows "Hello World")
GoldRateExtractor.java: The main program that does all the work
Technologies Used
Java 22: Modern Java version
Selenium WebDriver: Controls web browsers automatically
Apache POI: Creates and manages Excel files
WebDriverManager: Automatically manages browser drivers
How It Works (Step-by-Step)
Browser Setup (Lines 41-52): Automatically sets up Firefox browser
Excel Creation (Lines 60-72): Creates an Excel file with headers
Date Range (Lines 78-85): Defines the last 30 days to collect data
Data Collection Loop (Lines 91-169): For each date:
Opens the calendar on the website
Selects the specific date
Extracts gold prices from the webpage
Writes the data to Excel
Save File (Lines 177-184): Saves the Excel file as "Chennai_Gold_Rates.xlsx"
Cleanup (Lines 190-191): Closes the browser
The Line You Pointed To (Line 52)
java
System.out.println("Step 1: Site has been opened");
This is simply a console message that tells you the website has been successfully opened in the browser.

Why This is Useful
Data Collection: Automatically gathers historical gold price data
Analysis: The Excel file can be used for price trend analysis
Automation: Saves hours of manual data entry
Learning: Great example of web scraping and automation
This project demonstrates practical web automation skills that are valuable in data collection, testing, and business intelligence scenarios.

Feedback submitted



Command Awaiting Approval








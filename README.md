# Gold Price History Extractor

This repository contains code for extracting and analyzing gold rate history data in Chennai.

## 📋 Overview

The Gold Price History Extractor is a web automation and data processing application built with Java that scrapes gold price information and stores it in Excel format for historical analysis and tracking.

1. Opens a web browser automatically
2. Selects different dates (last 30 days) or Selects the specific date
3. Extracts gold prices for different purities (24K, 22K, 18K)
4. Writes the data to Excel
5. Save File (Lines 177-184): Saves the Excel file as "Chennai_Gold_Rates.xlsx"


## 🛠️ Tech Stack

- **Language**: Java 22
- **Build Tool**: Maven
- **Web Automation**: Selenium WebDriver 4.39.0
- **Data Processing**: Apache POI 5.5.1
- **WebDriver Management**: WebDriverManager 6.3.3
- **IDE**: IntelliJ IDEA (based on project configuration)

## 📦 Dependencies

The project uses the following key dependencies:

- **Selenium Java** (v4.39.0) - For web browser automation and web scraping
- **Apache POI** (v5.5.1) - For reading and writing Excel files
- **Apache POI OOXML** (v5.5.1) - For handling .xlsx format Excel files
- **WebDriverManager** (v6.3.3) - For automatic WebDriver management

All dependencies are managed through Maven as defined in `pom.xml`.

## 🚀 Getting Started

### Prerequisites

- Java 22 or higher
- Maven 3.6+
- Chrome/Chromium browser (for Selenium WebDriver)









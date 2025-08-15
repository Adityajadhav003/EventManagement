package BaseClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Libraryclass {

    // WebDriver instance shared across the framework
    public static WebDriver driver;

    // Properties object to hold configuration values
    public static Properties prop;

    // Method to load configuration from the config.properties file
    public void loadConfig() {
        try {
            prop = new Properties(); // Create Properties object
            FileInputStream ip = new FileInputStream("src/test/resources/config.properties"); // Load config file
            prop.load(ip); // Load properties into the object
        } catch (IOException e) {
            // Print error if config file fails to load
            System.out.println("Failed to load config.properties: " + e.getMessage());
        }
    }

    // Method to launch the browser specified in the config file
    public WebDriver launchBrowser() {
        loadConfig(); // Load the configuration

        String browserName = prop.getProperty("browser"); // Get browser name from properties

        if (browserName.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver(); // Launch Chrome browser
            driver.manage().window().maximize(); // Maximize browser window
        } else {
            // If browser not supported, print warning
            System.out.println("Browser not supported!");
        }

        return driver; // Return the WebDriver instance
    }
}

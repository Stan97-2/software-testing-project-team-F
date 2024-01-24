package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeTest {
    // Shared between all tests in this class.
    static Playwright playwright;
    static Browser browser;
    static String employeeName = "Employee";
    static String employeeMail = "employee@gmail.com";

    // New instance for each test method.
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    @Order(1)
    void createEmployee() {

        page.navigate("https://f.hr.dmerej.info/");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add new employee")).click();
        page.getByPlaceholder("Name").click();
        page.getByPlaceholder("Name").fill(employeeName);
        page.getByPlaceholder("Email").click();
        page.getByPlaceholder("Email").fill(employeeMail);
        page.locator("#id_address_line1").click();
        page.locator("#id_address_line1").fill("Address A");
        page.locator("#id_address_line2").click();
        page.locator("#id_address_line2").fill("Address B");
        page.getByPlaceholder("City").click();
        page.getByPlaceholder("City").fill("Paris");
        page.getByPlaceholder("Zip code").click();
        page.getByPlaceholder("Zip code").fill("75001");
        page.getByPlaceholder("Hiring date").fill("2024-01-24");
        page.getByPlaceholder("Job title").click();
        page.getByPlaceholder("Job title").fill("Tester");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName(Pattern.compile(employeeName))).last()).containsText(employeeName);
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName(Pattern.compile(employeeMail))).last()).containsText(employeeMail);
    }

    @Test
    @Order(2)
    void updateAddress() {
        page.navigate("https://f.hr.dmerej.info/");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List Employees")).click();
        page.getByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName(employeeName+" "+employeeMail)).getByRole(AriaRole.LINK).first().click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Update address")).click();
        String previousAddressA = page.locator("#id_address_line1").getAttribute("value");
        String previousAddressB = page.locator("#id_address_line2").getAttribute("value");

        assertThat(page.locator("#id_address_line1")).hasValue(previousAddressA);
        assertThat(page.locator("#id_address_line2")).hasValue(previousAddressB);

        page.locator("#id_address_line1").click();
        page.locator("#id_address_line1").fill("Address B");
        page.locator("#id_address_line2").click();
        page.locator("#id_address_line2").fill("Address D");

        String newAddressA = page.locator("#id_address_line1").getAttribute("value");
        String newAddressB = page.locator("#id_address_line2").getAttribute("value");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Update")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Update address")).click();

        assertThat(page.locator("#id_address_line1")).hasValue(newAddressA);
        assertThat(page.locator("#id_address_line2")).hasValue(newAddressB);
    }
}

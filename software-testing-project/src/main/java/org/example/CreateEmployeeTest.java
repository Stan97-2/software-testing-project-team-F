package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
public class CreateEmployeeTest {
    // Shared between all tests in this class.
    static Playwright playwright;
    static Browser browser;

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
    void createEmployee() {
        String employeeName = "Employee";
        String employeeMail = "employee@gmail.com";

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
}

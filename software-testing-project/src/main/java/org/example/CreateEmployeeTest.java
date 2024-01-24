package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.*;

public class CreateEmployeeTest {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = browser.newPage();
            page.navigate("https://f.hr.dmerej.info/");
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add new employee")).click();
            page.getByPlaceholder("Name").click();
            page.getByPlaceholder("Name").fill("Employee2");
            page.getByPlaceholder("Email").click();
            page.getByPlaceholder("Email").fill("employee2@gmail.com");
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
        }
    }
}

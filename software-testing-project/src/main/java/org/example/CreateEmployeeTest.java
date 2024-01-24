package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.*;
import java.util.regex.Pattern;

public class CreateEmployeeTest {
    public static void main(String[] args) {
        String employeeName = "Employee7";
        String employeeMail = "employee7@gmail.com";
        String employeeAddressA = "Address A";
        String employeeAddressB = "Address B";
        String employeeCity = "Paris";
        String employeeZipCode = "75001";
        String employeeHiringDate = "2024-01-24";
        String employeeJobTitle = "Tester";

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = browser.newPage();
            page.navigate("https://f.hr.dmerej.info/");
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add new employee")).click();
            page.getByPlaceholder("Name").click();
            page.getByPlaceholder("Name").fill(employeeName);
            page.getByPlaceholder("Email").click();
            page.getByPlaceholder("Email").fill(employeeMail);
            page.locator("#id_address_line1").click();
            page.locator("#id_address_line1").fill(employeeAddressA);
            page.locator("#id_address_line2").click();
            page.locator("#id_address_line2").fill(employeeAddressB);
            page.getByPlaceholder("City").click();
            page.getByPlaceholder("City").fill(employeeCity);
            page.getByPlaceholder("Zip code").click();
            page.getByPlaceholder("Zip code").fill(employeeZipCode);
            page.getByPlaceholder("Hiring date").fill(employeeHiringDate);
            page.getByPlaceholder("Job title").click();
            page.getByPlaceholder("Job title").fill(employeeJobTitle);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();

            assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName(Pattern.compile(employeeName))).last()).containsText(employeeName);
            assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName(Pattern.compile(employeeMail))).last()).containsText(employeeMail);

        }
    }
}

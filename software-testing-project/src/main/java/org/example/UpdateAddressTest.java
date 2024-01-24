package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.*;

public class UpdateAddressTest {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = browser.newPage();
            page.navigate("https://f.hr.dmerej.info/");
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List Employees")).click();
            page.getByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName("Employee7 employee7@gmail.com")).getByRole(AriaRole.LINK).first().click();
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
}

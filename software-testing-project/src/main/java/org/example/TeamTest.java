package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
public class TeamTest {
    static Playwright playwright;
    static Browser browser;
    static String teamName = "Team1";

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
    void createTeam() {
        page.navigate("https://f.hr.dmerej.info/");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create new team")).click();
        page.getByPlaceholder("Name").click();
        page.getByPlaceholder("Name").fill(teamName);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();

        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName(Pattern.compile(teamName))).last()).containsText(teamName);
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Delete")).last().click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed")).click();
    }
}

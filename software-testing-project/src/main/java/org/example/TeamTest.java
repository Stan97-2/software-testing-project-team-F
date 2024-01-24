package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import org.junit.jupiter.api.*;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(1)
    void createTeam() {
        page.navigate("https://f.hr.dmerej.info/");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create new team")).click();
        page.getByPlaceholder("Name").click();
        page.getByPlaceholder("Name").fill(teamName);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();

        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName(Pattern.compile(teamName))).last()).containsText(teamName);
    }

    @Test
    void addEmployeeToTeam() {
        page.navigate("https://f.hr.dmerej.info/employees");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Edit")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add to team")).click();
        page.getByLabel("Team").selectOption(new SelectOption().setIndex(1));
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
        page.navigate("https://f.hr.dmerej.info/teams");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("View members")).click();
        assertThat(page.getByRole(AriaRole.LISTITEM)).containsText("Employee");
    }

    @Test
    void deleteTeam() {
        page.navigate("https://f.hr.dmerej.info/teams");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Delete")).last().click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed")).click();
        assertThat(page.locator("body")).containsText("No teams yet");
    }

    @Test
    void isEmployeeDeleted() {
        page.navigate("https://f.hr.dmerej.info/employees");
        assertThat(page.getByRole(AriaRole.TABLE)).containsText("Employee");
    }
}

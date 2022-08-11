package ru.netology.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class DeliveryCardTest {
    long addDays = 5;

    @BeforeAll
    static void setUpAll() {
//        Configuration.headless = true;
    }

    @BeforeEach
    void setupTest() {
        open("http://localhost:9999");

    }

    @Test
    void shouldSubmitValidData() {
        $("[data-test-id=city] .input__control").setValue("Санкт-Петербург");
        String meetingDate = LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id = date] .input__control").doubleClick().sendKeys(meetingDate);
        $("[data-test-id = name] .input__control").setValue("Иван Иванов");
        $("[data-test-id = phone] .input__control").setValue("+79998887766");
        $("[data-test-id = agreement]").click();
        $(".button").click();
        $("[data-test-id = notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id = notification]").shouldHave(text("Успешно!"), Duration.ofSeconds(15));
    }

    @Test
    void shouldInputCityFromList() {
        $("[data-test-id=city] .input__control").setValue("ла");
        $$(".menu-item").first().click();
        String meetingDate = LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id = date] .input__control").doubleClick().sendKeys(meetingDate);
        $("[data-test-id = name] .input__control").setValue("Иван Иванов");
        $("[data-test-id = phone] .input__control").setValue("+79998887766");
        $("[data-test-id = agreement]").click();
        $(".button").click();
        $("[data-test-id = notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id = notification]").shouldHave(text("Успешно!"), Duration.ofSeconds(15));
    }

    @Test
    void shouldInputDateFromCalendar() {
        $("[data-test-id=city] .input__control").setValue("Са");
        $$(".menu-item").first().click();
        addDays = 7;
        String meetingDate = LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String calendarDay = meetingDate.substring(0, 2);
        $(".input__icon").click();
        $$(".calendar__day").find(text(calendarDay)).click();
        $("[data-test-id = name] .input__control").setValue("Иван Иванов");
        $("[data-test-id = phone] .input__control").setValue("+79998887766");
        $("[data-test-id = agreement]").click();
        $(".button").click();
        $("[data-test-id = notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id = notification]").shouldHave(text("Успешно!"), Duration.ofSeconds(15));
    }
}
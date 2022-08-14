package ru.netology.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {
    public String setMeetingDate(long countDays) {
        return LocalDate.now().plusDays(countDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String numberDay(String date) {
        String numberDay;
        if (date.substring(0, 1).contains("0")) {
            numberDay = date.substring(1, 2);
        } else {
            numberDay = date.substring(0, 2);
        }
        return numberDay;
    }

    @BeforeAll
    static void setUpAll() {
        Configuration.headless = true;
    }

    @BeforeEach
    void setupTest() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitValidData() {
        $("[data-test-id=city] .input__control").setValue("Санкт-Петербург");
        String meetingDate = setMeetingDate(7);
        $("[data-test-id = date] .input__control").doubleClick().sendKeys(meetingDate);
        $("[data-test-id = name] .input__control").setValue("Иван Иванов");
        $("[data-test-id = phone] .input__control").setValue("+79998887766");
        $("[data-test-id = agreement]").click();
        $(".button").click();
        $("[data-test-id = notification]")
                .shouldHave(text("Успешно!"), Duration.ofSeconds(15))
                .shouldBe(visible);
        $(".notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + meetingDate), Duration.ofSeconds(15))
                .shouldBe(visible);
    }

    @Test
    void shouldInputCityFromList() {
        $("[data-test-id=city] .input__control").setValue("ла");
        $$(".menu-item").first().click();
        String meetingDate = setMeetingDate(7);
        $("[data-test-id = date] .input__control").doubleClick().sendKeys(meetingDate);
        $("[data-test-id = name] .input__control").setValue("Иван Иванов");
        $("[data-test-id = phone] .input__control").setValue("+79998887766");
        $("[data-test-id = agreement]").click();
        $(".button").click();
        $("[data-test-id = notification]")
                .shouldHave(text("Успешно!"), Duration.ofSeconds(15))
                .shouldBe(visible);
        $(".notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + meetingDate), Duration.ofSeconds(15))
                .shouldBe(visible);
    }

    @Test
    void shouldInputDateFromCalendar() {
        $("[data-test-id=city] .input__control").setValue("Са");
        $$(".menu-item").first().click();
        $(".input__icon").click();
        long addDays = 7;
        String meetingDate = setMeetingDate(addDays);
        String calendarDay = numberDay(meetingDate);
        if (LocalDate.now().plusDays(addDays).getMonthValue() > LocalDate.now().getMonthValue()) {
            $("[data-step='1'].calendar__arrow_direction_right ").click();
            $$(".calendar__day").find(text(calendarDay)).click();
        } else {
            $$(".calendar__day").find(text(calendarDay)).click();
        }
        $("[data-test-id = name] .input__control").setValue("Иван Иванов");
        $("[data-test-id = phone] .input__control").setValue("+79998887766");
        $("[data-test-id = agreement]").click();
        $(".button").click();
        $("[data-test-id = notification]")
                .shouldHave(text("Успешно!"), Duration.ofSeconds(15))
                .shouldBe(visible);
        $(".notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + meetingDate), Duration.ofSeconds(15))
                .shouldBe(visible);
    }
}
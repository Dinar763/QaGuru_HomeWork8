import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;
import java.util.stream.Stream;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BankOpenAndAeroVolgaTest {

    @ValueSource(strings = {"кредиты", "ипотека", "вклады", "депозиты"})
    @ParameterizedTest(name = "Результаты поиска не пустые для запроса {0}")
    void checkHeader2(String testData) {
        open("https://www.open.ru/");
        $(".search-block-wrapper").click();
        $("[type='search']").setValue(testData).pressEnter();
        $$(".search-results__list").shouldBe(CollectionCondition.sizeGreaterThan(0));
    }

    @CsvSource(value = {
        "кредиты,  Льготные кредиты с господдержкой",
        "ипотека,  Ипотека",
    })
    @ParameterizedTest(name = "Результаты поиска содержат текст \"{1}\" для запроса: \"{0}\"")
    void commonComplexSearchTest(String testData, String expectedResult) {
        open("https://www.open.ru/");
        $(".search-block-wrapper").click();
        $("[type='search']").setValue(testData).pressEnter();
        $$(".search-results__list").first().shouldHave(text(expectedResult));
    }


    static Stream<Arguments> dataProviderForSelenideSiteMenuTest() {
        return Stream.of(
            Arguments.of(Lang.EN, List.of("NPO AEROVOLGA", "AIRCRAFT","GALLERY",
                "FOR AIRCRAFT OWNERS","PROJECT","CONTACTS")),
            Arguments.of(Lang.RU, List.of("НПО АЭРОВОЛГА", "ПРОДУКЦИЯ","ФОТОГАЛЕРЕЯ",
                "ВЛАДЕЛЬЦАМ","ПРОЕКТЫ","КОНТАКТЫ"))
        );
    }
    @MethodSource("dataProviderForSelenideSiteMenuTest")
    @ParameterizedTest(name = "Для локали {0} отображаются кнопки {1}")
    void aeroVolgaSiteMenuTest(Lang lang, List<String> expectedButtons) {
        open("http://www.aerovolga.com/ru/");
        $$(".langs td").find(text(lang.getDescription())).click();
        $$(".top-menu li")
            .filter(visible)
            .shouldHave(CollectionCondition.texts(expectedButtons));
    }
}

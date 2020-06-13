package xyz.dahun.events;

import junitparams.JUnitParamsRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;


@TestInstance( PER_CLASS )
class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder()
                .name("Inflearn Spring REST API")
                .description("ttt")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        //Given
        String name = "Event";
        String description = "Spring";

        //When
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        //Then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }


    @DisplayName("가격이 있거나 없으면")
    @ParameterizedTest
//    @CsvSource({
//            "0, 0, true",
//            "0, 100, false",
//            "100, 0, false",
//    })
    @MethodSource("isOffline1")
    public void testFree(int basePrice, int maxPrice, boolean isFree) {

        // Given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        // When
        event.update();

        //Then
        assertThat(event.isFree()).isEqualTo(isFree);
    }
    private Stream<Arguments> isOffline1(){
        return Stream.of(
        Arguments.of(0, 0, true),
        Arguments.of(0, 100, false),
        Arguments.of(100, 0, false)
        );
    }

//    @Test
    @DisplayName("오프라인 모임 테스트")
    @ParameterizedTest
    @MethodSource("isOffline")
    public void testOffline(String location, boolean isOffline) {
        // Given
        Event event = Event.builder()
                .location(location)
                .build();

        // When
        event.update();

        //Then
        assertThat(event.isOffline()).isEqualTo(isOffline);

    }

    private Stream<Arguments> isOffline(){
        return Stream.of(
                Arguments.of("강남", true),
                Arguments.of(null, false),
                Arguments.of("", false)
        );
    }
}
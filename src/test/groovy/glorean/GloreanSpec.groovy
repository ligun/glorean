package glorean


import spock.lang.Specification

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

class GloreanSpec extends Specification {
    static LOCAL_DATE_STRING = '1955-11-05T00:00:00'
    static OFFSET_DATE_STRING = LOCAL_DATE_STRING + ZoneOffset.systemDefault()
    static ZONED_DATE_STRING = OFFSET_DATE_STRING + '[' + ZoneId.systemDefault() + ']'

    def cleanup() {
        Glorean.back()
    }

    def "Can be set with string"() {
        when:
        Glorean.travelTo(date_string)
        def now = LocalDateTime.now()

        then:
        now == LocalDateTime.parse(LOCAL_DATE_STRING)

        where:
        date_string << [
                LOCAL_DATE_STRING,
                OFFSET_DATE_STRING,
                ZONED_DATE_STRING,
        ]
    }

    def "Can be set with LocalDateTime"() {
        when:
        def dateTime = LocalDateTime.parse(LOCAL_DATE_STRING)
        Glorean.travelTo(dateTime)
        def now = OffsetDateTime.now()

        then:
        now == OffsetDateTime.of(LocalDateTime.parse(LOCAL_DATE_STRING), ZoneOffset.systemDefault().offset)
    }

    def "Can be set with OffsetDateTime"() {
        when:
        def dateTime = OffsetDateTime.of(LocalDateTime.parse(LOCAL_DATE_STRING), ZoneId.systemDefault().offset)
        Glorean.travelTo(dateTime)
        def now = OffsetDateTime.now()

        then:
        now == OffsetDateTime.of(LocalDateTime.parse(LOCAL_DATE_STRING), ZoneId.systemDefault().offset)
    }

    def "Can be set with ZonedDateTime"() {
        when:
        def dateTime = ZonedDateTime.of(LocalDateTime.parse(LOCAL_DATE_STRING), ZoneId.systemDefault())
        Glorean.travelTo(dateTime)
        def now = ZonedDateTime.now()

        then:
        now == ZonedDateTime.of(LocalDateTime.parse(LOCAL_DATE_STRING), ZoneId.systemDefault())
    }

    def "Can be used with closure block"() {
        setup:
        def currentDate = LocalDateTime.parse '1985-10-26T00:00:00'
        Glorean.travelTo(currentDate)

        when:
        def travelDate
        Glorean.travelTo(date) {
            travelDate = LocalDateTime.now()
        }

        then:
        travelDate == LocalDateTime.parse(LOCAL_DATE_STRING)
        currentDate == LocalDateTime.now()

        where:
        date << [
                LOCAL_DATE_STRING,
                OFFSET_DATE_STRING,
                ZONED_DATE_STRING,
                LocalDateTime.parse(LOCAL_DATE_STRING),
                OffsetDateTime.parse(OFFSET_DATE_STRING),
                ZonedDateTime.parse(ZONED_DATE_STRING),
        ]
    }

    def "Check cleanup"() {
        expect:
        LocalDateTime.now() != LocalDateTime.parse(LOCAL_DATE_STRING)
    }

    def "Back to the future"() {
        setup:
        Glorean.travelTo(LOCAL_DATE_STRING)
        def future = LocalDateTime.now()

        when:
        Glorean.back()

        then:
        future != LocalDateTime.now()
    }
}

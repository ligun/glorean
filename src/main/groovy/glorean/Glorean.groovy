/*
Copyright 2021 ligun

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package glorean

import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException

class Glorean {
    static Clock clock = Clock.systemDefaultZone()

    static {
        LocalDateTime.metaClass.static.now = {->delegate.now(clock)}
        OffsetDateTime.metaClass.static.now = {->delegate.now(clock)}
        ZonedDateTime.metaClass.static.now = {->delegate.now(clock)}
    }

    static void travelTo(String date) {
        try {
            def dateTime = LocalDateTime.parse(date)
            travelTo(dateTime)
            return
        }
        catch (DateTimeParseException ignored) {}

        try {
            def dateTime = OffsetDateTime.parse(date)
            travelTo(dateTime)
            return
        }
        catch (DateTimeParseException ignored) {}

        try {
            def dateTime = ZonedDateTime.parse(date)
            travelTo(dateTime)
        }
        catch (DateTimeParseException e) { throw e }
    }

    static void travelTo(LocalDateTime dateTime) {
        def zoneId = ZoneId.systemDefault()
        travelTo(dateTime, zoneId)
    }

    static void travelTo(LocalDateTime dateTime, ZoneId zoneId) {
        travelTo(dateTime.toInstant(zoneId.offset), zoneId)
    }

    static void travelTo(OffsetDateTime dateTime) {
        def zoneId = ZoneId.systemDefault()
        travelTo(dateTime.toInstant(), zoneId)
    }

    static void travelTo(ZonedDateTime dateTime) {
        travelTo(dateTime.toInstant(), dateTime.zone)
    }

    static void travelTo(Instant instant, ZoneId zonId) {
        clock = Clock.fixed(instant, zonId)
    }

    static void travelTo(def obj, Closure cls) {
        def currentClock = clock
        travelTo(obj)
        cls()
        clock = currentClock
    }

    static void back() {
        clock = Clock.systemDefaultZone()
    }
}

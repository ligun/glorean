# glorean

A time travel library  
Inspired by the Ruby library [delorean](https://github.com/bebanjo/delorean)

Glorean can shift the time as you like.  
It support Java DateTime API.

Glorean is pronounced "dʒilɔriʌn".

## Usage

### Quick start

```groovy
import glorean.Glorean
import java.time.LocalDateTime


// Time travel to the past!
def past = '1955-11-05T06:00:00'
Glorean.travelTo(past)
assert LocalDateTime.now() == LocalDateTime.parse(past)

// Back to the future!
Glorean.back()
assert LocalDateTime.now() != LocalDateTime.parse(past)
```

### More functions

`Glorean.travelTo` method can be set String, LocalDateTime, OffsetDateTime or ZonedDateTime.
Format of string is supported ISO8061. (It uses parse method of DateTime API.)

```groovy
Glorean.travelTo('1955-11-05T06:00:00') // LocalDateTime
Glorean.travelTo('1955-11-05T06:00:00+09:00') // OffsetDateTime
Glorean.travelTo('1955-11-05T06:00:00+09:00[Asia/Tokyo]') // ZonedDateTime

def ldt = LocalDateTime.of(1955, 11, 05, 06, 00, 00)
Glorean.travelTo(ldt)
def odt = OffsetDateTime.of(ldt, ZoneOffset.ofHours(9))
Glorean.travelTo(odt)
def zdt = ZonedDateTime.of(ldt, ZoneId.of('Asia/Tokyo'))
Glorean.travelTo(zdt)
```

After set the time, you can get LocalDatetime, OffsetDateTime, ZonedDateTime.

```groovy
def zoneId = ZoneId.systemDefault()
def past = '1955-11-05T06:00:00'
Glorean.travelTo(past)

assert LocalDateTime.now() == LocalDateTime.parse(past)
assert OffsetDateTime.now() == OffsetDateTime.of(LocalDateTime.parse(past), zoneId.offset)
assert ZonedDateTime.now() == ZonedDateTime.of(LocalDateTime.parse(past), zoneId)
```

You are able to shift only partly by using closure as below.

```groovy
def current = '1985-10-26T01:35:00'
def past = '1955-11-05T06:00:00'

Glorean.travelTo(current)

Glorean.travelTo(past) { // Time travel only in the closure
    assert LocalDateTime.now() == LocalDateTime.parse(past)
}

// Automatically back to the future
assert LocalDateTime.now() == LocalDateTime.parse(current)
```
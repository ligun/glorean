# glorean

[![Java](https://img.shields.io/badge/Java-8+-4c7e9f.svg)](https://www.oracle.com/technetwork/java/javase/downloads)
[![Groovy](https://img.shields.io/badge/Groovy-3.0+-4c7e9f.svg)](https://groovy.apache.org/download.html)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.ligun/glorean/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.ligun/glorean)
[![Run unit test](https://github.com/ligun/glorean/actions/workflows/test.yaml/badge.svg)](https://github.com/ligun/glorean/actions/workflows/test.yaml)

A time travel library  
Inspired by the Ruby library [delorean](https://github.com/bebanjo/delorean)

Glorean can shift the time as you like.  
It support Java DateTime API.

Glorean is pronounced "dʒilɔriʌn".

## Installation
### Gradle

```groovy
dependencies {
    implementation 'net.ligun:glorean:1.0'
}
```

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
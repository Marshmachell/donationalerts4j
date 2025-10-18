[jitpack]: https://img.shields.io/badge/Snapshots-JitPack?logo=jitpack
[license]: https://github.com/Marshmachell/donationalerts4j/blob/master/LICENSE
[license-shield]: https://img.shields.io/badge/License-Apache%202.0-white.svg
[donation-alerts]: https://www.donationalerts.com/
[self]: https://github.com/Marshmachell/donationalerts4j/

[![jitpack][]](https://jitpack.io/#Marshmachell/donationalerts4j)
[![license-shield][]][license]

# donationalerts4j (Donation Alerts For Java)

This library is intented for implementing listeners [DonationAlerts][donation-alerts]

## 📖 Overview

[donationalerts4j][self] will allow you to listen to any notifications coming from your DonationAlerts account using the event system

## 🔬 Installation

[![jitpack][]](https://jitpack.io/#Marshmachell/donationalerts4j)

This library is available only on [JitPack](https://jitpack.io). The latest version is always shown in the [GitHub Release](https://github.com/Marshmachell/donationalerts4j/releases/latest).

The minimum java version supported by JDA is **Java SE 8**.

### Gradle

```gradle
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation("com.github.Marshmachell:donationalerts4j:$version") { // replace $version with the latest version
    }
}
```

### Maven

```xml
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
</repositories>

<dependency>
	    <groupId>com.github.Marshmachell</groupId>
	    <artifactId>donationalerts4j</artifactId>
	    <version>$version</version> <!-- replace $version with the latest version -->
</dependency>
```

### Example: Message Logging

Simply logging messages to the console.

Creating your DonationAlertsClient instance and attaching an event listener

```java
public static void main(String[] args) throws URISyntaxException {
        new DonationAlertsClient(token)
                .addEventListeners(new DonationReceiveListener())
                .build();
    }
```

Your event listener could look like this:

```java
public class DonationReceiveListener implements DonationListener {
    @Override
    public void onDonation(DonationEvent event) {
        System.out.printf("[%s] %s %s: %s\n",
                event.getUsername(),
                event.getAmountFormatted(),
                event.getCurrency(),
                event.getMessage());
    }
}
```
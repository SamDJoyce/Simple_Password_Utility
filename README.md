# Password Utility

**Version:** 1.0.0

## Overview

Password Utility is a reusable Java library that provides password hashing and verification using Argon2id.

The library wraps the Argon2 JVM implementation in a simple API to help Java applications securely hash passwords before storage and verify passwords during authentication.

## Features

- Argon2id password hashing
- Password verification against encoded hashes
- Automatic salt generation through the underlying library
- Configurable hashing parameters within the library implementation
- Input validation and documented exceptions
- Javadoc API documentation
- JUnit unit tests

## Requirements

- Java Development Kit (JDK) 17 or later
- Apache Maven
- The `de.mkammerer:argon2-jvm` dependency

## Installation

Download `simple-password-utility-1.0.0.jar` from the project's GitHub Releases page.

For a Maven project, add the following dependency after the library has been published to a Maven repository accessible to your project:

```xml
<dependency>
    <groupId>ca.samjoyce</groupId>
    <artifactId>password-utility</artifactId>
    <version>1.0.0</version>
</dependency>
```

If using the JAR directly or installing it locally, follow the project's installation instructions and ensure the Argon2 dependency is also available.

## Usage

Import the utility class:

```java
import ca.samjoyce.simple_password_utility.Simple_Password_Utility;
```

Hash a password:

```java
String hash = Simple_Password_Utility.hashPassword(
    "ExamplePassword123!".toCharArray()
);
```

Verify a password:

```java
boolean valid = PasswordUtility.verifyPassword(
    "ExamplePassword123!".toCharArray(),
    hash
);
```

The verification method returns `true` if the candidate password matches the stored hash and `false` if it does not.

## Security Considerations

- Store the encoded password hash, never the plaintext password.
- Do not log passwords or password hashes unnecessarily.
- The library wipes the supplied password character array after processing.
- Callers must not reuse a character array after passing it to either public method as it will be empty.
- Authentication systems should implement appropriate protections against automated login attempts and other common attacks.

## Building and Testing

Build the JAR:

```bash
mvn clean package
```

Run the unit tests:

```bash
mvn clean test
```

Generate the API documentation:

```bash
mvn javadoc:javadoc
```

The packaged JAR is generated under `target/`, and the HTML documentation is generated under `target/site/apidocs/`.

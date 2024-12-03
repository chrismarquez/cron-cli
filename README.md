
# Cron CLI

### Description

This project implements a small application to parse a cron string and display the expanded configuration into a time table.
The project is implemented in Kotlin as a CLI application.

### Dependencies

The project requires the GraalVM SDK as a dependency to compile and run the application.
The easiest way to install this dependency is as follows:
1. Run the following command to install SDKMan: `curl -s "https://get.sdkman.io" | bash`
   * For Windows, refer to the following [docs](https://sdkman.io/install/#windows-installation)
2. Using SDKMan, install the target SDK using: `sdk install java 23.0.1-graal`
3. Export the JAVA_HOME environment variable using: `export JAVA_HOME=$(sdk home java 23.0.1-graal)`

In addition, compiling native binaries (See Build & Run, Option 2) requires a native compilation toolchain.
You can check the requirements for each OS in the following [site](https://www.graalvm.org/latest/reference-manual/native-image/#prerequisites):    

### Build & Run

To build the project, run the following command:
`./gradlew build`

After building, the project can be run in three ways:
1. From Gradle: (Quick for development)
   * Gradle can take care of the execution of the app for quick development purposes
   * To run the app, execute the command: `./gradlew run --args="'cron string' <other-args>"`
   * Replace the value of 'cron-string' and <other-args> with the intended program arguments.
2. As a Native Application: (Intended way to distribute the app)
   * Gradle can compile a Native Binary of the application with the use of GraalVM. 
   * Make sure that the JAVA_HOME path variable points to a valid GraalVM SDK.
      * If you followed the GraalVM installation instructions, use: `export JAVA_HOME=$(sdk home java 23.0.1-graal)`
   * To compile the binary, run: `./gradlew nativeCompile`
   * The native binary file should be located in the `./build/native/nativeCompile` folder with the name `cron-cli`
   * To run the app, execute the command: `./build/native/nativeCompile "cron-string" <other-args>`
   * Replace the value of "cron-string" and <other-args> with the intended program arguments.
3. As a JVM Application: (Alternative way to run the app)
    * Gradle can compile a JAR file that can be run using the JVM
    * The JAR file should be located in the `./build/libs` folder with the name `cron-cli-1.0.jar`
    * To run the app, execute the command: `.java -jar ./build/libs/cron-cli-1.0.jar "cron-string" <other-args>`
    * Replace the value of "cron-string" and <other-args> with the intended program arguments.

Running the application with no parameters (or erroneous parameters) displays an error message that hints the user on the correct use of the app.

### Test
To run the project tests, run the following command:
`./gradlew test`

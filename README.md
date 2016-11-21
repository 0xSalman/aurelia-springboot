# Dotsub Assignment
A simple api that lets user upload files with some metadata (title, description and creation date), view files metadata
and download a single file. Technology stack is Java 8 + SpringBoot + MongoDB

## Requirements
- make (http://www.gnu.org/software/make/)
- gradle
- java 8
- (Windows Only) set $HOME environment variable

## Getting Started

### Running with make
1. `make -B build`
2. `make run`

### Running without make
1. `gradle clean build`
2. `java -jar ./build/libs/assignment-1.0.jar`

## Notes

1. Application default configurations are set in `src/main/resources/application.yml`. You can override them either by
creating OS environment variables or passing arguments to jvm when running the app.

When creating OS environment variable, naming format is all caps and replace `.` with `_`

 For example, if you want to change default logging dir,
 1. You can create a OS environment variable called `DSAS_LOGGING_DIR=` with your own path
 2. Or when running the app via IDE or commandline, you will pass `-Ddsas.logging.dir=` variable to jvm

2. By default the server runs on port `6010` and on *nix user home directory is the logging and file upload directories

3. To store file metadata, embedded mongodb is used which runs on port `27000`

4. The maximum file upload size is set to `10MB`

5. Configuration file, security and logging configs were added to make future enhancements easier. Similarly, custom exceptions
were added to make error handling easier for api clients

6. `StorageService` interface could have multiple implementations in the future and API could accept data that would determine
which storage implementation to use

7. `GET /api/files` takes in `Pageable` to let api clients implement pagination

8. Unit/Integration testing is very limited
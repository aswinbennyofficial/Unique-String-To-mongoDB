# Unique-String-To-mongoDB
The program generates random unique alphanumeric strings and uploads into mongodb database in defined schema. The default is 1 million 6 digits unique alphanumeric strings.

It is project done as a part of technocean event by Lovely Professional University. This project will be helpful if you are making a link shortner like bitly. 

## Requirements

- Add the following dependency on 'build.gradle'
- Edit Key.java and include your mongo db credentials in `String key="<include your mongodb creds>" ` 
- In the Main.java, replace the variable `capacity=<amount of strings required>` with your desired amount of unique strings to be uploaded.

```
dependencies {
    implementation 'org.mongodb:mongodb-driver-sync:4.9.0'
}```
- JDK used was 17.0.6

## Excecution
- Rename 'Key-example.java' in the above directory to 'Key.java' and insert the mongodb URi
- Edit or run the 'Main.java' file in the 'src/main/java/org/example' 
```


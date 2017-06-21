# robotAuthentification

## compile
`mvn clean install` generate 2 jar, first with no dependancies and oher with (`app.jar`). 

## Options
java -jar robotAuthentification-full.jar 
- -d <arg>   decrypt
- -e <arg>   encrypt
- -h         help
- -t <arg>   pause
- -z         use keyboard listener
 
## Use
- `java -jar App.jar some_data_encoding some_data_encoding2`
- `java -jar App.jar -z`
- `java -jar App.jar -e test`
- `java -jar App.jar -d 7nAsnRnwzGL+v/SsnQ4rXg==`

the option -t works with the 2 ways of use.

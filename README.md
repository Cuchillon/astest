#### Requirements
1. Java 14+
2. Gradle 6.5.1+
3. Intellij IDEA 2020.1.3+

#### Running tests
To run tests you need to clone the project and execute the next command in the root of the project:
```
./gradlew clean test -Ptags=pet // to run only pet tests
./gradlew clean test -Ptags=placeOrder // to run only place order test 
./gradlew clean test -Ptags=placeOrder,findOrder // to run place and find order tests
./gradlew clean test // to run all tests
```

To change web application URL or apiKey you should append the next parameters to the aforementioned commands:
```
-Pcommon.baseUrl=<your URL>
-Pcommon.apiKey=<your apiKey>
```

To generate allure report you should execute the next command in the root of project after running tests:
```
allure generate build/allure-results
``` 
After that you can find generated report in the 'allure-report' directory
#rest-api-testing-training
The projects is created for the demonstration purposes during REST API testing training.
It consists of two modules:
- auto-test-framework
- rest-assured-overview

##auto-test-framework
Demonstrates an example of the test automation framework based on the following tools:
- REST Assured
- TestNG
- AssertJ
- Allure

Use maven command to execute the tests and generate a test result report:
mvn -pl auto-test-framework
clean test
-DpetstoreBaseUri=http://petstore.swagger.io 
-DpetstoreApiKey=1234567890 
allure:report

##rest-assured-overview
Demonstrates basic REST Assured tool usage scenarios.
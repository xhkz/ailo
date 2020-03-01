### Task
Zombie Apocalypse v4.4

### Techs
* Java 8
* Gradle
* Lombok
* Spring Boot
* JUnit 5

### Run
* Bash
```bash
./gradlew bootRun
```
* Docker
```bash
./gradlew build
docker build --build-arg 'JAR_FILE=build/libs/*.jar' -t xhkz/ailo-code-challenge .
docker run -p 8080:8080 -t xhkz/ailo-code-challenge
```

### Access
```bash
# test app is up
curl 'http://localhost:8080/'
> Ailo Zombie Apocalypse by Xin Huang

# post input file
curl -X POST -F 'input=@/PATH/TO/INPUT_FILE' http://localhost:8080/run
> zombies score: 3
> zombies positions:
> (3,0)(2,1)(1,0)(0,0)
```

### Tests
```bash
./gradlew clean test
```

### Notes
1. The `model` and `service` contain the main logistics of the solution.
2. Hope my code can talk for me without comments covering 100%.
3. More test cases should be added to cover all scenarios.

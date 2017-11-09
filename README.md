# Manual Evaluation

A web application for comparing candidate answers to a baseline or gold standard data set.

## Requirements

1. Maven 3.x
1. [Spring Boot](https://spring.io)
1. Groovy is the implementation language as well as the language used for the [UI templates](https://spring.io/blog/2014/05/28/using-the-innovative-groovy-template-engine-in-spring-boot).
1. [Less](http://lesscss.org) is used to generate the CSS stylesheets.


## Building The Application

Run the following commands in the root directory of the project, that is, the directory containing the `pom.xml` file.

### Generating CSS

```bash
$> cd src/main/resources/static/css
$> lessc main.less main.css
```

### Building The Jar File.

```bash
$> mvn package
```

### Building The Docker Image

```bash
$> cp target/service.jar src/main/docker
$> docker build -t deiis/evaluator .
```

### Pushing The Docker Image

```bash
$> cp target/service.jar src/main/docker
$> docker tag deiis/evaluator docker.lappsgrid.org/deiis/evaluator
$> docker push docker.lappsgrid.org/deiis/evaluator
```

### Using Make

There is also a Makefile that can be used to perform all of the above tasks:

```bash
$> make clean
$> make jar
$> make style
$> make docker
$> make push
```

## Running The Application

Running the jar file:

```bash
$> java -jar target/service.jar
```

Running the Docker image:

```bash
$> docker run -d -p 8080:8080 --name evaluator docker.lappsgrid.org/deiis/evaluator
```


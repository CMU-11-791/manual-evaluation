JAR=service.jar
IMAGE=deiis/evaluator
REPO=docker.lappsgrid.org

help:
	@echo
	@echo "The following goals are available"
	@echo
	@echo "clean  - deletes build artifacts"
	@echo "jar    - generates the jar file"
	@echo "style  - generates the main.css file"
	@echo "run    - runs the jar file"
	@echo "docker - creates a Docker image"
	@echo "start  - starts the Docker container"
	@echo "stop   - stops and deletes the Docker container"
	@echo "push   - tags the Docker image and pushes it to docker.lappsgrid.org"
	@echo "help   - prints this help message."
	@echo

clean:
	mvn clean
	rm src/main/docker/$(JAR)

jar:
	mvn package
	cp target/$(JAR) src/main/docker

style:
	cd src/main/resources/static/css && lessc main.less main.css

run:
	java -jar target/$(JAR)

docker:
	cd src/main/docker && docker build -t $(IMAGE) .

start:
	docker run -d -p 8080:8080 --name evaluation $(IMAGE)

stop:
	docker rm -f evaluation

push:
	cd src/main/docker && \
	 docker tag $(IMAGE) $(REPO)/$(IMAGE) && \
	 docker push $(REPO)/$(IMAGE):latest

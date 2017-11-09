IMAGE=deiis/evaluator
REPO=docker.lappsgrid.org

help:
	echo "Help is needed."
	
clean:
	mvn clean
	rm src/main/docker/service.jar
	
jar:
	mvn package
	
docker:
	cp target/service.jar src/main/docker
	cd src/main/docker && docker build -t $(IMAGE) .
	
push:
	cd src/main/docker && \
	 docker tag $(IMAGE) $(REPO)/$(IMAGE) && \
	 docker push $(REPO)/$(IMAGE)

IMAGE=deiis/evaluator
REPO=docker.lappsgrid.org

help:
	echo "Help is needed."
	
clean:
	mvn clean
	rm src/main/docker/service.jar
	rm src/main/resources/static/css/main.css
	
jar:
	mvn package

style:
	cd src/main/resources/static/css && lessc main.less main.css
		
docker:
	cp target/service.jar src/main/docker
	cd src/main/docker && docker build -t $(IMAGE) .
	
push:
	cd src/main/docker && \
	 docker tag $(IMAGE) $(REPO)/$(IMAGE) && \
	 docker push $(REPO)/$(IMAGE)

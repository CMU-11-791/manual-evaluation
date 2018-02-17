FROM lappsgrid/ubuntu

COPY *.json /usr/local/share/deiis/repository/
COPY service.jar /root
CMD ["java", "-jar", "/root/service.jar"]

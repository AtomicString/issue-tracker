FROM eclipse-temurin:21.0.7_6-jre

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /home/wait-for-it
RUN chmod +x /home/wait-for-it
COPY ./tracker/target/tracker-0.0.1-SNAPSHOT-jar-with-dependencies.jar /home/tracker.jar

#CMD ["java", "-jar", "/home/tracker.jar"]

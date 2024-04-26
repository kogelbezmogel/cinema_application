FROM tomcat:10.1.13-jdk17

RUN rm -rvf /usr/local/tomcat/webapps/ROOT

ADD  /target/CinemaProjectId-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]

EXPOSE 8080
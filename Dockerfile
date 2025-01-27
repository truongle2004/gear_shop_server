FROM eclipse-temurin:23-jre-alpine
LABEL author="truongle"
COPY /target/jwt*.jar app.jar
ENV TZ=Asia/Ho_Chi_Minh
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT ["java", "-jar", "app.jar"]

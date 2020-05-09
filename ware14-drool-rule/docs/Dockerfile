FROM openjdk:8-jdk-alpine
VOLUME /opt
MAINTAINER smart.zhang
ADD drool-rule.jar drool-rule.jar

ENV TZ=Asia/Shanghai
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.ustc.edu.cn/g' /etc/apk/repositories
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENTRYPOINT ["java", "-jar", "-server", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=${ENV}", "-DisDefaultProcessor=${isDefaultProcessor}", "drool-rule.jar"]

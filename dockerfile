# 사실... 복잡할 필요가 없다
FROM openjdk

# 라벨은 컨테이너의 정보를 입력하는 란
LABEL maintainer=coding404

#볼륨생성
VOLUME ["/upload"]

# 변수
ARG jarfile=build/libs/BootMyweb-0.0.1.war

# 해당변수를 참조해서 컨테이너 최상위 경로에 app.war로 복사
COPY ${jarfile} /app.war


#
CMD ["java", "-jar", "/app.war"]

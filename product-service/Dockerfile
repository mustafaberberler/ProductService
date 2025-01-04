# 1. Base Image: OpenJDK kullanarak Java uygulamaları için temel ortam oluşturuyoruz.
FROM eclipse-temurin:17-jdk-alpine

# 2. Uygulama JAR dosyasını container içine kopyalamak için bir ARG kullanıyoruz.
ARG JAR_FILE=target/product-service-0.0.1-SNAPSHOT.jar

# 3. JAR dosyasını container içinde belirli bir dizine kopyalıyoruz.
COPY ${JAR_FILE} app.jar

# 4. Container'da uygulamayı başlatmak için giriş noktası komutunu tanımlıyoruz.
ENTRYPOINT ["java", "-jar", "/app.jar"]

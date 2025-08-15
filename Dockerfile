# 1. Use an official Maven image with Java 17

FROM maven:3.9.8-eclipse-temurin-17 AS builder
 
WORKDIR /app
 
# Copy only pom.xml first to leverage caching

COPY pom.xml .

RUN mvn dependency:go-offline -B
 
# Copy source code and test suite

COPY src ./src

COPY testng.xml .
 
# Build project without running tests

RUN mvn clean package -DskipTests
 
# -------------------------

# Runtime container WITH Maven

# -------------------------

FROM maven:3.9.8-eclipse-temurin-17
 
WORKDIR /app

COPY --from=builder /app /app
 
# Install Google Chrome for Selenium tests

RUN apt-get update && apt-get install -y \

    wget \

    gnupg \

    unzip \
&& wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
&& echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" \
> /etc/apt/sources.list.d/google-chrome.list \
&& apt-get update && apt-get install -y google-chrome-stable \
&& rm -rf /var/lib/apt/lists/*
 
# Install ChromeDriver

RUN DRIVER_VERSION=$(curl -sS https://chromedriver.storage.googleapis.com/LATEST_RELEASE) && \

    wget -O /tmp/chromedriver.zip "https://chromedriver.storage.googleapis.com/${DRIVER_VERSION}/chromedriver_linux64.zip" && \

    unzip /tmp/chromedriver.zip -d /usr/local/bin && \

    rm /tmp/chromedriver.zip
 
# Chrome options to prevent sandbox errors in Docker

ENV CHROME_OPTS="--no-sandbox --disable-dev-shm-usage"
 
# Run tests

CMD ["mvn", "test"]

 
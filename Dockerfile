FROM openjdk:11-jdk-slim

# Install Maven and ChromeDriver
RUN apt-get update && apt-get install -y curl tar unzip
ARG MAVEN_VERSION=3.6.3
ARG USER_HOME_DIR="/root"
RUN mkdir -p /usr/share/maven && \
curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz |  \
    tar -xzC /usr/share/maven --strip-components=1 && \
ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

RUN curl -fsSL https://chromedriver.storage.googleapis.com/87.0.4280.20/chromedriver_linux64.zip -o /tmp/chromedriver.zip \
    && unzip /tmp/chromedriver.zip -d /usr/local/bin


# Set the working directory
WORKDIR /app

# Copy the project files
COPY . .

# Build the project
RUN mvn clean install

# Set the entrypoint to run the tests
ENTRYPOINT mvn verify
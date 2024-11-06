# Use OpenJDK 17 as the base image
FROM openjdk:17-slim-buster

# Install dependencies for building Python from source
RUN apt-get update && \
    apt-get install -y wget build-essential zlib1g-dev libncurses5-dev \
    libgdbm-dev libnss3-dev libssl-dev libreadline-dev libffi-dev curl && \
    apt-get clean

# Download and install Python 3.9 from source
RUN wget https://www.python.org/ftp/python/3.9.17/Python-3.9.17.tgz && \
    tar -xf Python-3.9.17.tgz && \
    cd Python-3.9.17 && \
    ./configure --enable-optimizations && \
    make && \
    make altinstall && \
    cd .. && \
    rm -rf Python-3.9.17 Python-3.9.17.tgz

# Install pip for Python 3.9
RUN curl -sS https://bootstrap.pypa.io/get-pip.py | python3.9

# Create symbolic links for easy access to Python and pip
RUN ln -s /usr/local/bin/python3.9 /usr/bin/python && \
    ln -s /usr/local/bin/pip3.9 /usr/bin/pip

# Install yt-dlp using pip
RUN pip install yt-dlp

# Create necessary directories for logging and download data
RUN mkdir -p /logs /logs/archived /downloaddata

# Expose the port your Spring Boot application will run on
EXPOSE 8080

# Copy the Spring Boot application JAR file into the container
ADD target/download.jar download.jar

# Verify Python and pip installation (optional)
RUN python --version && pip --version

# Set the entrypoint to start the Spring Boot application
ENTRYPOINT ["java", "-jar", "download.jar"]

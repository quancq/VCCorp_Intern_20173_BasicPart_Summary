FROM ubuntu:16.04
MAINTAINER QuanChu <chuquocquan96@gmail.com>

# Update package
RUN apt-get update

# Install JDK
RUN apt-get -y install default-jdk

# Set work directory
WORKDIR /home/quanchu/vcc_intern_20173/tool/bt2

# Copy resource
COPY run_jar_every_seconds.sh .
COPY tool_bt2-0.0.1-SNAPSHOT-jar-with-dependencies.jar .

CMD ["./run_jar_every_seconds.sh", "./tool_bt2-0.0.1-SNAPSHOT-jar-with-dependencies.jar", "5"]

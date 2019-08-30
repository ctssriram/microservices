# Start with a base image containing Java runtime 
FROM openjdk:8-jdk-alpine 
 
# Add Maintainer Info 
LABEL maintainer="sriramchander.c" 
 
# Add the application's jar to the container 
ADD target/product-service-0.0.1-SNAPSHOT.jar /opt/product-service.jar 
  
# Expose the application port 
EXPOSE 9001 
 
ENTRYPOINT ["java","-jar","/opt/product-service.jar"] 
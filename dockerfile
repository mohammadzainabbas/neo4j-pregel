FROM openjdk:11
RUN apt-get update && apt-get install -y libxext6 libxrender1 libxtst6 libfreetype6 fontconfig
CMD ["jconsole"]

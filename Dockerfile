FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y unzip git

USER ubuntu

ENV PATH=$PATH:/opt/nvim-linux-x86_64/bin
ARG KOTLIN_LSP_VERSION=262.8190.0

WORKDIR /home/ubuntu
RUN mkdir .config && mkdir .local && mkdir .gemini
RUN wget "https://download-cdn.jetbrains.com/language-server/kotlin-server/${KOTLIN_LSP_VERSION}/kotlin-server-${KOTLIN_LSP_VERSION}.tar.gz" && \
    wget "https://download-cdn.jetbrains.com/language-server/kotlin-server/${KOTLIN_LSP_VERSION}/kotlin-server-${KOTLIN_LSP_VERSION}.tar.gz.sha256" && \
    sha256sum -c kotlin-server-${KOTLIN_LSP_VERSION}.tar.gz.sha256 && \
    tar -xzvf kotlin-server-${KOTLIN_LSP_VERSION}.tar.gz && \
    rm kotlin-server-${KOTLIN_LSP_VERSION}.tar.gz

RUN wget https://github.com/fwcd/kotlin-debug-adapter/releases/download/0.4.4/adapter.zip && \
    unzip adapter.zip
ENV PATH=$PATH:/home/ubuntu/kotlin-server-${KOTLIN_LSP_VERSION}/bin:/home/ubuntu/.local/bin:/home/ubuntu/adapter/bin


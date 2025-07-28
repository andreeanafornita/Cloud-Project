FROM critoma/amd64_u24_noble_ism_security

WORKDIR /app

COPY package*.json /app/
COPY index.js /app/
COPY start.sh /app/
COPY init.sql /app/init.sql

RUN apt-get update && apt-get install -y \
    nodejs npm \
    mysql-server \
    wget gnupg

RUN npm install

RUN chmod +x /app/start.sh

EXPOSE 3000 3306

CMD ["bash", "/app/start.sh"]
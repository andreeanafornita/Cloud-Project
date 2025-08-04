📷 Distributed BMP Image Processing Platform
A distributed system for uploading, processing, and storing BMP images using a microservices architecture with Docker, Java, Node.js, MySQL, and ActiveMQ.

This platform showcases advanced software engineering concepts such as RMI, JMS, REST APIs, and container orchestration — ideal for educational or demo purposes.

🚀 Features
🎨 Web Interface: Intuitive frontend (website/index.html) for uploading BMP images and selecting zoom factor.

🔄 Asynchronous Workflow: Image processing is triggered via JMS messages using ActiveMQ.

🔧 Microservices Architecture:

C01 – REST API: Receives BMP + zoom factor, sends JMS message.

C02 – Embedded ActiveMQ: Message broker.

C03 – JMS Consumer: Calls RMI servers (C04/C05), sends result to C06.

C04 & C05 – Java RMI servers: Handle image zoom operations.

C06 – Node.js REST API: Stores final image in MySQL, serves for download.

🧠 RMI-based Processing: Zooming is done in parallel using Java's image libraries.

💾 MySQL Storage: Images are stored as BLOBs and can be retrieved.

🐳 Dockerized: All components run in isolated containers via Docker Compose.

🗺️ Architecture Overview
text
Copy
Edit
User ➜ Web Interface ➜ C01 (REST + JMS) ➜ ActiveMQ (C02)
     ↘                                 ↘
      C03 (JMS Listener) ➜ RMI to C04 & C05 ➜ C06 (Node.js + MySQL)
                                                 ⬇
                                          Image stored in DB
🛠️ Technologies Used
Tech	Role
Java 17	Javalin, RMI, JMS, ActiveMQ
Node.js	Express, Multer, MySQL2
MySQL 5.7	Image storage (BLOB)
Python 3	Static HTTP servers in containers
HTML/CSS/JS	Frontend interface
Docker	Containerization
Docker Compose	Multi-container orchestration

🧪 How to Run
From the root folder (Project_DAD):

bash
Copy
Edit
docker-compose build
docker-compose up
👉 Then navigate to the website/ directory and open index.html in your browser.

🖱️ Usage
Upload a .bmp image.

Select a zoom factor.

Wait for processing (via RMI + JMS).

Download the final processed image.

📁 Project Structure
graphql
Copy
Edit
Project_DAD/
├── c01/            # Image upload (REST), JMS producer
├── c02/            # Embedded ActiveMQ broker
├── c03/            # JMS consumer, RMI client, HTTP client
├── c04/, c05-copy/ # RMI servers for zooming
├── c06/            # Node.js server, MySQL integration
├── website/        # Web UI
├── docker-compose.yml
└── my.cnf          # MySQL config
📜 License
This project is intended for educational purposes only.

Vrei să ți-l transform într-un README.md direct sau să ți-l urc acum pe GitHub?









You said:
vreau sa folosesi codul folosit de read me pt a fi formatat la perfectie


ChatGPT said:
Perfect! Mai jos ai un fișier README.md complet formatat, cu tot conținutul organizat în stil profesionist GitHub, folosind Markdown corect și elegant:

md
Copy
Edit
# 📷 Distributed BMP Image Processing Platform

A distributed system for uploading, processing, and storing BMP images using a **microservices architecture** with **Docker**, **Java**, **Node.js**, **MySQL**, and **ActiveMQ**.

> This platform showcases advanced software engineering concepts such as **RMI**, **JMS**, **REST APIs**, and **container orchestration** — ideal for educational or demo purposes.

---

## 🚀 Features

- 🎨 **Web Interface**: Intuitive frontend (`website/index.html`) for uploading BMP images and selecting zoom factor.
- 🔄 **Asynchronous Workflow**: Image processing is triggered via JMS messages using ActiveMQ.
- 🧩 **Microservices Architecture**:
  - **C01** – REST API: Receives BMP + zoom factor, sends JMS message.
  - **C02** – Embedded ActiveMQ: Message broker.
  - **C03** – JMS Consumer: Calls RMI servers (C04/C05), sends result to C06.
  - **C04 & C05** – Java RMI servers: Handle image zoom operations.
  - **C06** – Node.js REST API: Stores final image in MySQL, serves for download.
- 🧠 **RMI-based Processing**: Zooming is done in parallel using Java's image libraries.
- 💾 **MySQL Storage**: Images are stored as BLOBs and can be retrieved.
- 🐳 **Dockerized**: All components run in isolated containers via Docker Compose.

---

## 🗺️ Architecture Overview

```text
User ➜ Web Interface ➜ C01 (REST + JMS) ➜ ActiveMQ (C02)
     ↘                                 ↘
      C03 (JMS Listener) ➜ RMI to C04 & C05 ➜ C06 (Node.js + MySQL)
                                                 ⬇
                                          Image stored in DB
🛠️ Technologies Used
Technology	Description
Java 17	Javalin, RMI, JMS, ActiveMQ
Node.js	Express, Multer, MySQL2
MySQL 5.7	Binary image storage
Python 3	Static file servers in some containers
HTML/CSS/JS	Frontend interface
Docker	Containerization of all components
Docker Compose	Orchestration of services

▶️ How to Run
From the Project_DAD root directory:

bash
Copy
Edit
docker-compose build
docker-compose up
Then navigate to the website/ directory and open index.html in your browser.

💡 Usage
Upload a .bmp image.

Select a zoom factor.

Wait for processing (via RMI + JMS).

Download the final processed image from the interface.

📁 Project Structure
graphql
Copy
Edit
Project_DAD/
├── c01/            # Image upload (REST), JMS producer
├── c02/            # Embedded ActiveMQ broker
├── c03/            # JMS consumer, RMI client, HTTP client
├── c04/, c05-copy/ # RMI servers for zooming
├── c06/            # Node.js server, MySQL integration
├── website/        # Web UI
├── docker-compose.yml
└── my.cnf          # MySQL configuration
📜 License
This project is intended for educational purposes only.

yaml
Copy
Edit

---

### ✅ Ce poți face acum:
1. Salvează conținutul de mai sus într-un fișier local numit `README.md` în folderul `Project_DAD`.
2. Rulează următoarele comenzi pentru a-l urca pe GitHub:

```bash
cd ~/Desktop/Project_DAD
git add README.md
git commit -m "Actualizez README cu descriere completă"
git push
Vrei să-ți creez acest fișier automat sau să continui tu manual?









You said:
da mi totul intr un singur loc ca sa pot doar copy paste


ChatGPT said:
Perfect, Andreea! Mai jos ai tot conținutul README.md într-un singur bloc, complet formatat. Poți da copy-paste direct în fișierul README.md din proiectul tău Project_DAD.

✅ Conținut complet README.md (GitHub Friendly)
md
Copy
Edit
# 📷 Distributed BMP Image Processing Platform

A distributed system for uploading, processing, and storing BMP images using a **microservices architecture** with **Docker**, **Java**, **Node.js**, **MySQL**, and **ActiveMQ**.

> This platform showcases advanced software engineering concepts such as **RMI**, **JMS**, **REST APIs**, and **container orchestration** — ideal for educational or demo purposes.

---

## 🚀 Features

- 🎨 **Web Interface**: Intuitive frontend (`website/index.html`) for uploading BMP images and selecting zoom factor.
- 🔄 **Asynchronous Workflow**: Image processing is triggered via JMS messages using ActiveMQ.
- 🧩 **Microservices Architecture**:
  - **C01** – REST API: Receives BMP + zoom factor, sends JMS message.
  - **C02** – Embedded ActiveMQ: Message broker.
  - **C03** – JMS Consumer: Calls RMI servers (C04/C05), sends result to C06.
  - **C04 & C05** – Java RMI servers: Handle image zoom operations.
  - **C06** – Node.js REST API: Stores final image in MySQL, serves for download.
- 🧠 **RMI-based Processing**: Zooming is done in parallel using Java's image libraries.
- 💾 **MySQL Storage**: Images are stored as BLOBs and can be retrieved.
- 🐳 **Dockerized**: All components run in isolated containers via Docker Compose.

---

## 🗺️ Architecture Overview

```text
User ➜ Web Interface ➜ C01 (REST + JMS) ➜ ActiveMQ (C02)
     ↘                                 ↘
      C03 (JMS Listener) ➜ RMI to C04 & C05 ➜ C06 (Node.js + MySQL)
                                                 ⬇
                                          Image stored in DB
🛠️ Technologies Used
Technology	Description
Java 17	Javalin, RMI, JMS, ActiveMQ
Node.js	Express, Multer, MySQL2
MySQL 5.7	Binary image storage
Python 3	Static file servers in some containers
HTML/CSS/JS	Frontend interface
Docker	Containerization of all components
Docker Compose	Orchestration of services

▶️ How to Run
From the Project_DAD root directory:

bash
Copy
Edit
docker-compose build
docker-compose up
Then navigate to the website/ directory and open index.html in your browser.

💡 Usage
Upload a .bmp image.

Select a zoom factor.

Wait for processing (via RMI + JMS).

Download the final processed image from the interface.

📁 Project Structure
graphql
Copy
Edit
Project_DAD/
├── c01/            # Image upload (REST), JMS producer
├── c02/            # Embedded ActiveMQ broker
├── c03/            # JMS consumer, RMI client, HTTP client
├── c04/, c05-copy/ # RMI servers for zooming
├── c06/            # Node.js server, MySQL integration
├── website/        # Web UI
├── docker-compose.yml
└── my.cnf          # MySQL configuration

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

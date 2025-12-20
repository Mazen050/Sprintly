# E-Commerce Website (Angular + Spring Boot) ![Vercel Deploy](https://deploy-badge.vercel.app/vercel/sprintly-front)

## 📌 Overview

The **E-Commerce Website** is a full‑stack web application built using **Angular** for the frontend and **Spring Boot** for the backend. It provides a complete online shopping experience, including product browsing, authentication, cart management, checkout workflow, and admin functionality.


## 🚀 Live Demo

🔗 **Production Deployment:**  
https://sprintly-front.vercel.app/

Backend: [here](https://peculiar-ginni-mazen212-2562c12b.koyeb.app/)

---

## 🚀 Features

### **Frontend (Angular)**

* Responsive UI built with Angular
* Product listing, categories, search & filtering
* User authentication (JWT‑based)
* Shopping cart + wishlist functionality
* User profile & order history

### **Backend (Spring Boot)**

* RESTful API architecture
* JWT authentication & authorization
* CRUD operations for products, categories, orders, users
* PostgreSQL database integration
* Global exception handling & validation

---

## 🛠 Tech Stack

### **Frontend:**

* Angular
* TypeScript
* HTML / CSS


### **Backend:**

* Spring Boot
* Spring Security (JWT)
* Spring Data JPA
* PostgreSQL
* Maven

---

# 🏗️ Project Setup Instructions

## 📁 1. Clone the Repository

```bash
git clone https://github.com/Mazen050/Sprintly.git
cd Sprintly
```

---

# 🔧 Backend Setup (Spring Boot)

### 📌 **Prerequisites**

* Java 17+
* Maven 3+
* PostgreSQL Server

### 📄 **Configure Environment Variables**

Create an `application.properties` file in `/backend/src/main/resources/`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/Sprintly
spring.datasource.username=YOUR_POSTGRES_USERNAME
spring.datasource.password=YOUR_POSTGRES_PASSWORD
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
jwt.secret=YOUR_SECRET_KEY
```

### ▶️ **Run Backend Server**

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

The backend will run on:

```
http://localhost:8080
```

---

# 🎨 Frontend Setup (Angular)

### 📌 **Prerequisites**

* Node.js 18+
* Angular CLI

Install Angular CLI if needed:

```bash
npm install -g @angular/cli
```



### ▶️ **Install Dependencies**

```bash
cd frontend
npm install
```

### ▶️ **Run Frontend App**

```bash
ng serve 
```

The frontend will run on:

```
http://localhost:4200
```

---

# ⚙️ Build Commands

### **Frontend:**

```bash
ng build
```

### **Backend:**

```bash
mvn package
```

---

# 🧩 Folder Structure

```
Sprintly/
│── backend/ # Spring Boot API
│── frontend/ # Angular UI
│── database/ # Supabase / DB schema & migrations
│── .github/ # Workflows & CI/CD configs
│── README.md # Documentation
│── .gitignore # Git ignore rules
```




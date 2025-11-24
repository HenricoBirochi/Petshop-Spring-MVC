# 🐾 Petshop E-Commerce

> A full-featured pet shop e-commerce web application built with **Spring Boot MVC** and **Thymeleaf**.

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Alpine-blue?logo=postgresql)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3-green?logo=thymeleaf)
![License](https://img.shields.io/badge/License-MIT-yellow)

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🛒 **Shopping Cart** | Add products, adjust quantities, stored in cookies |
| 📦 **Product Management** | CRUD operations with image upload support |
| 👤 **User Authentication** | Sign up, sign in with session-based auth |
| 🔐 **Role-based Access** | Admin-only routes protected via custom AOP annotations |
| 📋 **Order Management** | Place orders, view order history |
| 🖼️ **Image Upload** | Multiple product images with file storage |
| 🎨 **Responsive UI** | Clean templates with Thymeleaf fragments |

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| **Backend** | Java 21, Spring Boot 3.5.7, Spring MVC, Spring Data JPA, Spring AOP |
| **Frontend** | Thymeleaf, HTML5, CSS3 |
| **Database** | PostgreSQL (via Docker) |
| **Security** | BCrypt (jBCrypt), Custom AOP Annotations |
| **Build** | Maven |
| **Other** | Lombok, HikariCP |

---

## 📁 Project Structure

```
src/main/java/voyager/petshop/
├── 🔐 authentication/       # Custom annotations & AOP aspects
│   ├── VerifyIfIsAdmin.java
│   ├── VerifyIfIsAdminAspect.java
│   ├── VerifyIfLogged.java
│   └── VerifyIfLoggedAspect.java
├── ⚙️ config/               # Configuration & data seeding
│   └── DataSeeder.java
├── 🎮 controllers/          # MVC Controllers
│   ├── CartController.java
│   ├── MainController.java
│   ├── OrderController.java
│   ├── ProductController.java
│   └── UserController.java
├── 📝 dtos/                 # Data Transfer Objects
│   ├── Cart.java
│   ├── CartItem.java
│   └── LoginForm.java
├── ⚠️ exceptions/           # Custom exceptions
├── 📦 models/               # JPA Entities
│   ├── Order.java
│   ├── Product.java
│   ├── User.java
│   └── enums/
├── 🗄️ repositories/         # Spring Data JPA Repositories
└── 🔧 services/             # Business logic services
```

---

## 🚀 Getting Started

### Prerequisites

- ☕ **Java 21** or higher
- 🐳 **Docker** & Docker Compose
- 📦 **Maven** (or use the included `mvnw` wrapper)

### 1️⃣ Clone the repository

```bash
git clone https://github.com/HenricoBirochi/Petshop-Spring-MVC.git
cd Petshop-Spring-MVC
```

### 2️⃣ Start the database

```bash
docker-compose up -d
```

This will start a PostgreSQL container on port `5432` with:
- 🗄️ Database: `my_db`
- 👤 User: `root`
- 🔑 Password: `root`

### 3️⃣ Run the application

**Using Maven Wrapper:**

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/macOS
./mvnw spring-boot:run
```

**Or using Maven directly:**

```bash
mvn spring-boot:run
```

### 4️⃣ Access the application

🌐 Open your browser and navigate to: **http://localhost:8080**

---

## 🔐 Authentication & Authorization

This project uses **custom AOP annotations** for access control:

| Annotation | Description |
|------------|-------------|
| `@VerifyIfLogged` | Ensures user is authenticated |
| `@VerifyIfIsAdmin` | Ensures user has ADMIN role |

**User Roles:**
- 👤 `USER` - Regular customer
- 👑 `ADMIN` - Can manage products

---

## 📸 Screenshots

> 💡 *Add screenshots of your application here!*

---

## 🗂️ API Endpoints

### 🏠 Main
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/` | Home page | 🔓 Public |
| `GET` | `/about` | About page | 🔓 Public |

### 📦 Product
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/product/all-products` | List all products | 🔓 Public |
| `GET` | `/product/details/{id}` | Product details | 🔓 Public |
| `GET` | `/product/add` | Add product form | 👑 Admin |
| `POST` | `/product/add` | Create product | 👑 Admin |
| `GET` | `/product/edit/{id}` | Edit product form | 👑 Admin |
| `POST` | `/product/edit/{id}` | Update product | 👑 Admin |
| `POST` | `/product/delete/{id}` | Delete product | 👑 Admin |

### 👤 User
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/user/sign-up` | Register page | 🔓 Public |
| `POST` | `/user/sign-up` | Register user | 🔓 Public |
| `GET` | `/user/sign-in` | Login page | 🔓 Public |
| `POST` | `/user/sign-in` | Authenticate user | 🔓 Public |
| `GET` | `/user/sign-out` | Logout user | 🔓 Public |

### 🛒 Cart
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/cart/view` | View cart | 🔓 Public |
| `POST` | `/cart/add-item` | Add item to cart | 🔓 Public |
| `POST` | `/cart/remove-item` | Remove item from cart | 🔓 Public |
| `GET` | `/cart/checkout` | Checkout cart | 🔒 Logged |

### 📋 Orders
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/orders` | List user orders | 🔒 Logged |

---

## ⚙️ Configuration

Key settings in `application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/my_db

# File upload limits
spring.servlet.multipart.max-file-size=100MB
spring.servlet.multipart.max-request-size=100MB

# JPA
spring.jpa.hibernate.ddl-auto=update
```

---

## 🧪 Running Tests

```bash
# Windows
mvnw.cmd test

# Linux/macOS
./mvnw test
```

---

## 📝 License

This project is licensed under the MIT License.

---

## 👨‍💻 Authors

| 👤 Name | 🎓 RA | 🐙 GitHub |
|---------|-------|-----------|
| **Nicholas Birochi** | 081230038 | [@nicholasbirochi](https://github.com/nicholasbirochi) |
| **Henrico Birochi** | 081230027 | [@HenricoBirochi](https://github.com/HenricoBirochi) |
| **Vítor Agostino Braghittoni** | 081230024 | [@VBraghittoni](https://github.com/VBraghittoni) |
| **Edgar Camacho Seabra Ribeiro** | 081230039 | [@Edgarcsr](https://github.com/Edgarcsr) |

---

## 🤝 Contributing

Contributions are welcome! Feel free to:

1. 🍴 Fork the project
2. 🌿 Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. 💾 Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. 📤 Push to the branch (`git push origin feature/AmazingFeature`)
5. 🔃 Open a Pull Request

---

## ⭐ Show your support

Give a ⭐ if this project helped you!

---

<p align="center">Made with ❤️ and ☕ by Nicholas, Henrico, Vítor & Edgar</p>

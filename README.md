# RK Plastics & Enterprises - Invoice Management System (Backend)

A robust, scalable, and production-ready RESTful backend built for **RK Plastics & Enterprises Invoice Management System**. The backend provides APIs for customer management, GST invoice generation, PDF creation, Excel export, reporting, and business analytics.

---

# 📌 Overview

This backend powers the complete Invoice Management System by handling

- Customer Management
- Invoice Management
- GST Calculation
- PDF Invoice Generation
- Excel Report Export
- Dashboard Analytics
- Sales Reports
- Monthly Statistics

The application is built using Java Enterprise technologies following a clean layered architecture.

---

# ✨ Features

## Dashboard APIs

- Total Invoices
- Today's Sales
- Monthly Sales
- Latest Invoice
- GST Collected
- Dashboard Statistics

---

## Customer APIs

- Add Customer
- Update Customer
- Delete Customer
- Search Customer
- Get All Customers
- Search by Name

---

## Invoice APIs

- Create Invoice
- Update Invoice
- Delete Invoice
- View Invoice
- View All Invoices
- Next Bill Number Generator

---

## GST Calculation

Supports

- CGST
- SGST
- IGST

Automatic tax calculation based on customer GST details.

---

## PDF Generation

Generate professional GST invoices containing

- Company Information
- Customer Details
- GST Details
- Invoice Items
- Tax Summary
- Grand Total
- Amount in Words

---

## Reports

Generate

- Dashboard Report
- Monthly Sales Report
- GST Summary
- Invoice Report
- Date Range Report

---

## Export Features

- Download Invoice PDF
- Download All Invoices PDF
- Download Date Range PDF
- Export Excel Report

---

## REST APIs

RESTful APIs built for

- React Frontend
- Mobile Applications
- Third-party Integration

---

# 🏗 Architecture

```
Client

        │

        ▼

Servlet Controller

        │

        ▼

Service Layer

        │

        ▼

DAO Layer

        │

        ▼

Hibernate ORM

        │

        ▼

MySQL Database
```

---

# 🛠 Tech Stack

## Backend

- Java 21
- Jakarta Servlet
- Hibernate ORM
- MySQL
- Apache Tomcat
- Maven

---

## Libraries

- Hibernate
- Gson
- Apache POI
- iText PDF
- MySQL Connector/J

---

## Database

- Railway MySQL

---

## Deployment

- Render

---

# 📂 Project Structure

```
src

│

├── controller
│
├── service
│
├── DAO
│
├── model
│
├── dto
│
├── util
│
├── filter
│
├── constants
│
└── exception
```

---

# ⚙ Configuration

Clone repository

```bash
git clone https://github.com/pritish9156/rkplastic-invoice-management-backend.git
```

Go inside project

```bash
cd rkplastic-invoice-management-backend
```

Build project

```bash
mvn clean install
```

Run on Tomcat Server

---

# 🔐 Environment Variables

Configure the following variables before deployment.

```
DB_URL=
DB_USERNAME=
DB_PASSWORD=
```

Example

```
DB_URL=jdbc:mysql://host:port/database?useSSL=true&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=your_password
```

---

# 📌 API Endpoints

## Customer APIs

```
GET     /api/customers
GET     /api/customers/search
POST    /api/customers
PUT     /api/customers/{id}
DELETE  /api/customers/{id}
```

---

## Invoice APIs

```
GET     /api/bills/all
GET     /api/bills/{id}
GET     /api/bills/next-bill-no

POST    /api/bills

PUT     /api/bills/{id}

DELETE  /api/bills/{id}

GET     /api/bills/{id}/pdf
```

---

## Reports APIs

```
GET /api/reports/dashboard

GET /api/reports/gst

GET /api/reports/monthly

GET /api/reports/date-range

GET /api/reports/download/all-pdf

GET /api/reports/download/date-pdf

GET /api/reports/download/excel
```

---

# 📄 PDF Generation

Professional invoice PDFs include

- Company Details
- Customer Details
- GST Information
- Invoice Items
- Tax Summary
- Grand Total
- Amount in Words

---

# 📊 Reports

Generate

- Dashboard Statistics
- Monthly Sales
- GST Summary
- Invoice Summary
- Excel Reports
- Bulk PDF Reports

---

# 🗃 Database Entities

- Customer
- Bill
- BillItem

Relationships

```
Customer

    │

    └────────── Bill

                    │

                    └────────── BillItem
```

---

# 🌐 CORS

Configured for

- Local Development
- Production Deployment (Vercel)

---

# 🚀 Deployment

Backend

- Render

Database

- Railway MySQL

Frontend

- Vercel

---

# 📈 Performance Features

- Hibernate Session Management
- Optimized Queries
- Environment Variable Support
- Modular Service Layer
- DAO Pattern
- Reusable Utilities

---

# 🔮 Future Improvements

- Spring Boot Migration
- JWT Authentication
- Role Based Authorization
- Product Management
- Inventory Management
- Purchase Orders
- Payment Tracking
- Email Invoice
- WhatsApp Invoice
- Barcode Generation
- Multi Company Support
- Docker Support
- Unit Testing
- CI/CD Pipeline

---

# 👨‍💻 Developed By

**Pritish Ramesh Pawar**

Java Full Stack Developer

### Skills

- Java
- Hibernate
- Servlets
- JDBC
- MySQL
- REST APIs
- React
- Spring Boot

---

## GitHub

https://github.com/pritish9156

## LinkedIn

https://www.linkedin.com/in/pritishpawar/

---

# ⭐ Support

If you found this project useful, please consider giving the repository a ⭐ on GitHub.

Your support motivates me to build more production-ready Java applications.

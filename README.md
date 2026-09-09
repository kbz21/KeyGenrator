# 🔐 Key Generator

A full-stack web application for generating and managing cryptographic key pairs, built with **Spring Boot** and **Vue.js**.

The project demonstrates practical concepts in **cryptography, cybersecurity, public-key cryptography, and PKI**, while providing a simple web interface for key generation, visualization, history, and export.

---

## 📌 Overview

Cryptographic keys are fundamental building blocks of modern cybersecurity.

They are used in technologies such as:

- Digital signatures
- TLS/HTTPS
- X.509 certificates
- Public Key Infrastructure (PKI)
- Secure communication
- Authentication
- Encryption

This project provides a practical environment for understanding how asymmetric cryptographic keys are generated and used in security-oriented applications.

The application consists of a **Vue.js frontend** and a **Spring Boot backend** communicating through a REST API.

---

## ✨ Features

### 🔑 Cryptographic Key Generation

Generate asymmetric cryptographic key pairs through a web interface.

The application provides controls for:

- Cryptographic algorithm selection
- Key-size selection
- Key-pair generation
- Public key display
- Private key display

---

### 📋 Key Management

Users can manage generated key pairs through the application interface.

Features include:

- View generated keys
- View key information
- Browse key history
- Select generated keys
- Display public/private key material

---

### 📤 Key Export

Generated key information can be exported for development and educational purposes.

---

### 📋 Clipboard Support

The application provides clipboard functionality for copying generated key information.

---

### 🌙 Light and Dark Themes

The application supports multiple visual themes:

- Light mode
- Dark mode
- Theme switching

---

### 📱 Responsive Interface

The frontend is designed to provide a usable experience across different screen sizes.

---

## 🧠 Cryptography Concepts

### Asymmetric Cryptography

Asymmetric cryptography uses two mathematically related keys:

```text
              Key Pair
                 │
        ┌────────┴────────┐
        │                 │
   Public Key        Private Key
        │                 │
   Can be shared       Must be
                       protected

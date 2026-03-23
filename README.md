# 🍔 Online Food Ordering System - API REST Soda Saira
```bash
API backend para un sistema de pedidos en línea de una soda/restaurante, desarrollada como Proyecto Final de Graduación. 
Permite a los clientes realizar pedidos en línea y a los administradores gestionar productos, usuarios y pedidos. Incluye autenticación JWT, 
roles diferenciados e integración con pasarela de pagos Stripe.
```

🔗 Frontend: Repositorio React https://github.com/Perez8899/online-food

## 🚀 Tecnologías Utilizadas
```bash
Categoría	Tecnologías
Lenguaje	Java 17
Framework	Spring Boot 3.3.1, Spring Security
Base de Datos	MySQL
Persistencia	Spring Data JPA, Hibernate
Autenticación	JWT (JSON Web Token)
Pagos	Stripe API
Frontend	React, Vite, Tailwind CSS, MUI
Herramientas	Lombok, Maven, Postman
```

## 👥 Roles del Sistema
```bash
Rol	Permisos
Cliente	Visualizar productos, realizar pedidos, ver historial de pedidos
Admin	CRUD de productos, gestión de usuarios, ver todos los pedidos
```

## Instalación
 ```bash
1. Clona el repositorio:
  
   git clone https:github.com/Perez8899/foodOrderinPerezJavaSpring
cd foodOrderinPerezJavaSpring


2. Configurar base de datos
Crear una base de datos en MySQL:

CREATE DATABASE online_food_db;
Configurar credenciales en src/main/resources/application.properties:

properties
spring.datasource.url=jdbc:mysql://localhost:3306/online_food_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

3. Configurar Stripe
Agregar tu clave secreta de Stripe en application.properties:
stripe.secret.key=sk_test_xxxxxxxxxxxxx

5. Ejecutar el backend
bash
mvn spring-boot:run
El backend estará disponible en: http://localhost:8080

6. Ejecutar el frontend
bash
git clone https://github.com/Perez8899/online-food.git
cd online-food
npm install
npm run dev
```

👨‍💻 Autor
```bash
Héctor José Pérez

GitHub: @Perez8899

Email: hectorjp43@gmail.com

Proyecto Final de Graduación - Universidad Católica de Costa Rica
```

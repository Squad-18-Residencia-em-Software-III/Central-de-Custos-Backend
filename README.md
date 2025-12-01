# 🏛️ Central de Custos SEDUC - Squad 18

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

> **Sistema web centralizado** para registrar, acompanhar e analisar despesas da rede estadual, organizando dados por níveis educacionais e garantindo transparência, padronização e agilidade na gestão dos recursos.

---

## 🏗️ Estrutura do Projeto

Este projeto adota uma arquitetura moderna dividida em:

| Componente | Stack Principal |
| :--- | :--- |
| **Backend** | Java 17 + Spring Boot |
| **Frontend** | React.js + TypeScript |

A aplicação é agnóstica de infraestrutura, podendo rodar **Localmente** ou via **Docker Compose**.

## 📚 Tecnologias Utilizadas

<details>
<summary><strong>Backend (API & Dados)</strong></summary>

*   **Linguagem:** Java 17
*   **Framework:** Spring Boot
*   **Segurança:** Spring Security + JWT
*   **Banco de Dados:** PostgreSQL
*   **Migração:** Flyway
*   **Containerização:** Docker

</details>

<details>
<summary><strong>Frontend (Interface)</strong></summary>

*   **Framework:** React.js
*   **Linguagem:** TypeScript
*   **Build Tool:** Vite
*   **Estilização:** Tailwind CSS
*   **Http Client:** Axios

</details>

---

# 🚀 Como Rodar a Aplicação

Escolha a abordagem que melhor se adapta ao seu cenário:

## 🐳 Opção 1: Via Docker (Recomendado para Produção/Homologação)

Ambiente completo rodando via orquestração de containers.

### ✔️ Requisitos
*   Docker & Docker Compose
*   Openssl

### ▶ Passo a passo

1.  **Clone o repositório de deploy:**
    ```bash
    git clone https://github.com/Squad-18-Residencia-em-Software-III/deploy-central-de-custos.git
    cd deploy-central-de-custos
    ```

2.  **Configure as variáveis de ambiente:**
    Crie um arquivo `.env` na raiz da pasta:
    ```bash
    nano .env
    ```
    *Cole o conteúdo abaixo:*
    ```properties
    SPRING_PROFILES_ACTIVE=prod
    DB_USERNAME=defina o usuario do banco
    DB_PASSWORD=defina a senha do banco
    DB_URL=jdbc:postgresql://centraldecustosdb:5432/centraldecustosdb
    DEFAULT_ADMIN_PASSWORD=defina a senha do usuario Admin padrão da aplicação
    EMAIL_SERVICE_URL=http://servico-email:8082/mail
    MAIL_PASSWORD=insira a senha de app do email que irá utilizar
    MAIL_USERNAME=insira o email que irá utilizar para o serviço
    REACT_APP_API_URL=http://centraldecustos-app:8080
    ```

3.  **Gere as chaves de segurança (JWT):**
    ```bash
    mkdir jwt
    cd jwt/
    
    # Gerar private/public key (RSA 2048)
    openssl genpkey -algorithm RSA -out app.key -pkeyopt rsa_keygen_bits:2048
    openssl rsa -in app.key -pubout -out app.pub
    ```

4.  **Suba os containers:**
    ```bash
    docker compose up -d --build
    ```

---

## 🔧 Opção 2: Executar Localmente (Perfil `dev`)

Ideal para desenvolvimento e debug.

### ✔️ Requisitos
*   Java 17+ & Maven
*   Node.js 18+ & NPM (ou Yarn)
*   PostgreSQL instalado (ou via Docker isolado)

### ▶ Backend (API)

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/Squad-18-Residencia-em-Software-III/Central-de-Custos-Backend.git
    cd Central-de-Custos-Backend
    ```

2.  **Inicie o Banco de Dados:**
   *   **Método A (Local):** Crie um banco chamado `dcentraldecustosdb` na porta `5432`.
   *   **Método B (Docker):** Rode o compose interno do repositório:
       ```bash
       docker compose up -d --build
       ```

3.  **Gere as chaves JWT:**
    ```bash
    cd src/main/resources/
    
    # Gerar private/public key (RSA 2048)
    openssl genpkey -algorithm RSA -out app.key -pkeyopt rsa_keygen_bits:2048
    openssl rsa -in app.key -pubout -out app.pub
    ```

4.  **Clone o serviço de Email (Opcional):**
    ```bash
    git clone https://github.com/queijobrando/Mail-Service.git
    cd Central-de-Custos-Backend
    ```

5.  **Rode a aplicação (Perfil DEV):**
    ```bash
    mvn spring-boot:run -Dspring-boot.run.profiles=dev
    ```

6.  **Configuração de Email (Opcional):**
    ```bash
    MAIL_PASSWORD=senha_do_seu_email;MAIL_USERNAME=email_para_envios
    ```

### ▶ Frontend (Web)

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/Squad-18-Residencia-em-Software-III/Central-de-Custos-Frontend.git
    cd Central-de-Custos-Frontend
    ```

2.  **Instale as dependências:**
    ```bash
    npm install
    ```

3.  **Execute o projeto:**
    ```bash
    npm run dev
    ```

---

## 📍 Endereços da Aplicação

Após a execução, os serviços estarão disponíveis nos seguintes endereços:

| Serviço | Descrição | URL / Porta |
| :--- | :--- | :--- |
| **Backend (API)** | Aplicação Spring Boot | `http://localhost:8080` |
| **Frontend (Web)** | Aplicação React + Vite | `http://localhost:3000` |
| **PostgreSQL** | Banco de dados | `http://localhost:5432` |
| **pgAdmin** | Gerenciamento do banco (Opcional) | `http://localhost:15432` |
# Central-de-Custos-Backend
Repositório do backend da aplicação Centra de Custos da Rede Estadual de Ensino desenvolvido pelo Squad 18


## 🌳 Estrutura de Branches

- `main`: código estável e pronto para produção (🚫 ninguém comita diretamente aqui)
- `develop`: onde as funcionalidades são integradas após revisão
- `feat/nome-da-feature`: onde cada pessoa trabalha em uma funcionalidade específica

---

## 🚀 Começando uma nova feature

```bash
# Atualize sua branch develop local
git checkout develop
git pull origin develop

# Crie sua branch de tarefa a partir de develop
git checkout -b feat/nome-da-feature

# Exemplo:
# git checkout -b feat/cadastro-usuario
```

---

## 💻 Trabalhando na sua branch

```bash
# Após fazer mudanças no código
git add .
git commit -m "feat(nome-da-feature): Implementa [descrição da tarefa]"

# Envie sua branch para o GitHub
git push origin feat/nome-da-feature
```

Se sua branch não for uma Feature necessáriamente, existem essas opções de commits / nomenclaturas:
- **feat**: nova funcionalidade

- **fix**: correção de bug

- **docs**: mudanças só em documentação

- **style**: mudanças de formatação (espaços, identação, etc.), sem alterar lógica

- **refactor**: refatoração sem mudar comportamento

- **test**: adicionar ou ajustar testes

- **chore**: tarefas de manutenção (configs, dependências, build...)

---

## 🔁 Criando um Pull Request (PR)

1. Vá até o repositório no GitHub.
2. Clique em **"Compare & pull request"** ou vá na aba **Pull Requests**.
3. Selecione:
    - **Base**: `develop`
    - **Compare**: `feat/nome-da-feature`
4. Descreva o que foi feito e envie para revisão.
5. Aguarde aprovação e merge.

---

## 🔄 Mantendo sua branch atualizada

Se alguém já tiver dado merge em `develop`, atualize a sua:

```bash
# Atualize develop
git checkout develop
git pull origin develop

# Volte para sua feature branch
git checkout feat/nome-da-feature

# Mescle as mudanças da develop na sua branch
git merge develop

# Resolva conflitos, se houver, e continue trabalhando normalmente
```

---

## ✅ Finalizando

Quando a branch `develop` estiver com várias funcionalidades testadas e estável, um **responsável** faz o merge dela para `main` via Pull Request:

```bash
git checkout main
git pull origin main
git merge develop
git push origin main
```

---

## 📌 Regras importantes

- 🔒 **Nunca comitar direto em `main` ou `develop`**
- ✅ **Sempre trabalhe em branches `feat/nome-da-feature`**
- 🔄 **Atualize sua branch com `develop` com frequência**
- 🧪 **Teste antes de pedir merge**
- 🧠 **Nomeie bem seus commits e branches**

---

👥 Time colaborando com responsabilidade = projeto saudável 🚀


## 📖 Documentação Swagger
`http://localhost:8080/swagger-ui.html`

## BANCO H2
`http://localhost:8080/h2-console/`

---

# ⚙️ Execução Local (Perfil `dev`)

Ambiente de desenvolvimento local para o backend da Central de Custos.

## 🔧 Configuração esperada

| Item           | Valor                                    |
| -------------- | ---------------------------------------- |
| Porta          | `8080`                                   |
| Banco de Dados | PostgreSQL → `dcentraldecustosdb`        |
| Usuário/Senha  | `dev` / `dev`                            |
| JWT Keys       | `src/main/resources/app.key` e `app.pub` |

---

## 🚀 Execução Rápida

```bash
# 1. Clonar e entrar no projeto
git clone https://github.com/Squad-18-Residencia-em-Software-III/Central-de-Custos-Backend.git
cd Central-de-Custos-Backend
git checkout develop
```

---

## 🗄️ Banco de Dados (PostgreSQL)

```sql
CREATE USER dev WITH PASSWORD 'dev';
CREATE DATABASE dcentraldecustosdb OWNER dev;

\c dcentraldecustosdb
GRANT ALL PRIVILEGES ON DATABASE dcentraldecustosdb TO dev;
GRANT ALL PRIVILEGES ON SCHEMA public TO dev;
```

---

## 🔐 Chaves JWT (local)

```bash
# Gerar private/public key (RSA 2048)
openssl genpkey -algorithm RSA -out app.key -pkeyopt rsa_keygen_bits:2048
openssl rsa -in app.key -pubout -out app.pub

# Mover para src/main/resources/
mv app.key app.pub src/main/resources/
```

---

## 🪪 Testar autenticação

1. Acesse o endpoint `/auth/login` diretamente no **Swagger UI**  
   👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

2. No corpo da requisição, informe o CPF padrão do administrador:

```json
{
  "cpf": "22411451067",
  "senha": "12345"
}
````

3. Execute a requisição — a resposta trará um campo **`token`** (JWT).

4. Copie o valor do token retornado.

5. Clique no botão **"Authorize"** no canto superior direito do Swagger e cole o token no formato:

```
Bearer SEU_TOKEN_AQUI
```

6. Após autorizar, todos os endpoints protegidos estarão liberados para uso com perfil **ADMIN** ✅

---

## 🧰 Dicas & Troubleshooting

* ❌ `URISyntaxException`: verifique se o profile `dev` está ativo.
* 🔑 `FileNotFound`: confirme `app.key` e `app.pub` em `src/main/resources`.
* 🐘 Erro JDBC: verifique permissões e owner do banco.
* 🔧 Flyway falha: execute `CREATE EXTENSION` como superuser.

---

## 🔒 Boas práticas

* Nunca comite chaves privadas.
* Use `prod` profile em produção.
* Configure secrets via variáveis de ambiente ou secret manager.

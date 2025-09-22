# Central-de-Custos-Backend
Repositório do backend da aplicação Centra de Custos da Rede Estadual de Ensino desenvolvido pelo Squad 18


## 🌳 Estrutura de Branches

- `main`: código estável e pronto para produção (🚫 ninguém comita diretamente aqui)
- `develop`: onde as funcionalidades são integradas após revisão
- `feat(nome-da-feature)`: onde cada pessoa trabalha em uma funcionalidade específica

---

## 🚀 Começando uma nova feature

```bash
# Atualize sua branch develop local
git checkout develop
git pull origin develop

# Crie sua branch de tarefa a partir de develop
git checkout -b feat(nome-da-feature)

# Exemplo:
# git checkout -b feat(cadastro-usuario)
```

---

## 💻 Trabalhando na sua branch

```bash
# Após fazer mudanças no código
git add .
git commit -m "feat(nome-da-feature): Implementa [descrição da tarefa]"

# Envie sua branch para o GitHub
git push origin feat(nome-da-tarefa)
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
    - **Compare**: `feat(nome-da-tarefa`)
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
git checkout feat(nome-da-tarefa)

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
- ✅ **Sempre trabalhe em branches `feat(nome-da-tarefa)`**
- 🔄 **Atualize sua branch com `develop` com frequência**
- 🧪 **Teste antes de pedir merge**
- 🧠 **Nomeie bem seus commits e branches**

---

👥 Time colaborando com responsabilidade = projeto saudável 🚀


## 📖 Documentação Swagger
`http://localhost:8080/swagger-ui.html`

## BANCO H2
`http://localhost:8080/h2-console/`

# AppAgendamentos

Aplicativo Android nativo para gerenciamento de agendamentos e compromissos, desenvolvido com **Jetpack Compose**, **Material 3** e **SQLite nativo (`SQLiteOpenHelper`)**.

---

## 🚀 Funcionalidades

- **Listagem de Agendamentos:** Visualização de todos os compromissos cadastrados com data, descrição e status.
- **Cadastro e Edição:** Criação e alteração de agendamentos com seletor de data interativo (*DatePicker*).
- **Conclusão de Tarefas:** Marcar compromissos como concluídos.
- **Exclusão:** Remoção de agendamentos do sistema.
- **Persistência Local:** Armazenamento seguro e offline utilizando SQLite.

---

## 🛠️ Tecnologias e Arquitetura

- **Linguagem:** Kotlin
- **UI:** Jetpack Compose & Material 3
- **Navegação:** Jetpack Navigation Compose
- **Arquitetura:** MVVM (`AndroidViewModel`)
- **Banco de Dados:** SQLite (`SQLiteOpenHelper` com `ContentValues` e `Cursor`)

---

## 📂 Estrutura do Projeto

```text
app/src/main/java/com/github/gleberphant/appagendamentos/
├── controllers/       # Gerenciadores de estado e lógica de negócio (AgendamentoController)
├── data/              # Camada de dados e SQLite (AgendamentoDbHelper)
├── models/            # Modelos de dados (Agendamento)
└── views/             # Telas e navegação (PageListarAgendamentos, PageCriaAgendamento, AppNavigator)
```

---

## ⚙️ Como Executar

1. Clone o repositório ou abra a pasta do projeto no **Android Studio**.
2. Aguarde a sincronização do Gradle.
3. Conecte um dispositivo Android ou inicie um Emulador (API 26 ou superior).
4. Clique em **Run** (`▶`) no Android Studio.

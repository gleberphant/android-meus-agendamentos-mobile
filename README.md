# AppAgendamentos ⚖️

Aplicativo Android nativo para **agenda coletiva corporativa de escritório de advocacia**, desenvolvido com **Kotlin**, **Jetpack Compose**, **Material 3** e **Firebase Cloud Firestore**.

O aplicativo permite a gestão compartilhada em tempo real de prazos processuais, audiências, reuniões e compromissos entre todos os advogados e colaboradores do escritório.

---

## 🚀 Funcionalidades

- **Agenda Coletiva em Tempo Real:** Sincronização instantânea via Firebase Cloud Firestore. Alterações feitas por qualquer advogado refletem na tela de toda a equipe sem necessidade de atualizar manualmente.
- **Filtro de Exibição:** Botão de alternância (*Mostrar Concluídos* / *Ocultar Concluídos*) para focar nos compromissos pendentes ou visualizar o histórico completo.
- **Cadastro e Edição de Agendamentos:** Criação e alteração de prazos com seletor de data interativo (*Material 3 DatePicker*).
- **Conclusão de Compromissos:** Marcar audiências e tarefas como concluídas com atualização em tempo real para a equipe.
- **Exclusão de Registros:** Remoção de agendamentos obsoletos ou cancelados.

---

## 🛠️ Tecnologias e Arquitetura

- **Linguagem:** Kotlin
- **UI & Design:** Jetpack Compose & Material 3 (M3)
- **Navegação:** Jetpack Navigation Compose
- **Arquitetura:** MVVM (`AndroidViewModel`) com `SnapshotStateList` (`mutableStateListOf`)
- **Banco de Dados & Nuvem:** Firebase Cloud Firestore (`com.google.firebase:firebase-firestore` via Firebase BoM)
- **Sincronização:** Escutadores em tempo real (`addSnapshotListener`)

---

## 📂 Estrutura do Projeto

```text
app/src/main/java/com/github/gleberphant/appagendamentos/
├── controllers/       # Lógica de negócio e integração Firebase em tempo real (AgendamentoController)
├── models/            # Modelo de dados (Agendamento)
└── views/             # Interface do usuário em Jetpack Compose
    ├── AppNavigator.kt            # Grafo de navegação (NavHost)
    ├── PageListarAgendamentos.kt  # Tela principal de listagem e filtros
    └── PageCriaAgendamento.kt    # Formulário de criação e edição com DatePicker
```

---

## ⚙️ Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/gleberphant/AppAgendamentos.git
   ```
2. **Configuração do Firebase:**
   - Crie um projeto no [Console do Firebase](https://console.firebase.google.com/).
   - Adicione um aplicativo Android com o pacote `com.github.gleberphant.appagendamentos`.
   - Baixe o arquivo **`google-services.json`** e adicione-o na pasta `app/` do projeto (`app/google-services.json`).
   - Ative o **Cloud Firestore Database** no Console do Firebase.
3. Abra o projeto no **Android Studio**.
4. Aguarde a sincronização do Gradle.
5. Execute o aplicativo em um emulador ou dispositivo físico (Android 8.0+ / API 26+).

# Documento de Requisitos de Produto (PRD) - AppAgendamentos (Agenda Coletiva Jurídica)

## 1. Visão Geral do Projeto
O **AppAgendamentos** é um aplicativo Android nativo desenvolvido para uso corporativo em um **escritório de advocacia**. O sistema atua como uma **agenda coletiva e compartilhada em tempo real** para todos os advogados e colaboradores do escritório, permitindo a gestão centralizada de prazos processuais, audiências, reuniões com clientes e diligências diretamente no dispositivo móvel, com armazenamento persistente em nuvem e sincronização em tempo real via **Firebase Cloud Firestore**.

---

## 2. Justificativa de Criação e Benefícios para a Empresa
A criação desta solução mobile colaborativa para o escritório justifica-se pelos seguintes pilares estratégicos e operacionais:
- **Agenda Coletiva em Tempo Real:** Todos os advogados e assistentes do escritório compartilham a mesma lista de compromissos. Qualquer alteração ou novo agendamento inserido por um membro da equipe fica visível instantaneamente para todos os demais.
- **Transparência e Cobertura de Audiências/Diligências:** Facilita a redistribuição de tarefas e o acompanhamento de prazos jurídicos cruciais, garantindo que nenhum compromisso do escritório seja esquecido ou fique sem atendimento.
- **Aumento de Produtividade e Organização:** Centralizar a agenda corporativa em tempo real substitui agendas físicas e planilhas descentralizadas, reduzindo erros operacionais e melhorando a comunicação interna.
- **Sincronização em Nuvem via Firebase:** Elimina a necessidade de servidores próprios, oferecendo persistência confiável na nuvem do Google com escutadores em tempo real (*Snapshot Listeners*).

---

## 3. Público-Alvo e Usuários
- **Advogados do Escritório:** Profissionais que necessitam consultar, cadastrar, editar e acompanhar os compromissos coletivos durante audiências, deslocamentos e reuniões externas.
- **Colaboradores e Assistentes Jurídicos:** Equipe de apoio operacional que gerencia e organiza o fluxo de agendamentos e reuniões de toda a equipe jurídica.

---

## 4. Requisitos Funcionais (Histórias de Usuário)

### 4.1. Listagem de Compromissos Coletivos e Filtro de Concluídos
- **Descrição:** O usuário visualiza a lista compartilhada de agendamentos do escritório na tela principal.
- **Detalhes:**
  - Exibição otimizada em lista (`LazyColumn`).
  - Cada compromisso exibe a data formatada (`dd/MM/yyyy`), descrição/assunto jurídico, status (*pendente* ou *concluído*) e botões de ação rápida (*Editar*, *Concluir*, *Excluir*).
  - **Filtro de Concluídos:** Botão de alternância (*Ocultar Concluídos* / *Mostrar Concluídos*) para filtrar a exibição dos compromissos concluídos e manter a tela focada nos pendentes.

### 4.2. Cadastro de Novo Agendamento
- **Descrição:** O advogado ou colaborador registra um novo compromisso na agenda coletiva.
- **Detalhes:**
  - Seleção de data facilitada por diálogo interativo (*Material 3 DatePicker*).
  - Inserção da descrição detalhada do compromisso ou prazo.
  - Salvamento síncrono no Firebase Cloud Firestore, sincronizando instantaneamente para todos os dispositivos conectados.

### 4.3. Edição de Agendamento
- **Descrição:** Atualização das informações de um compromisso já existente.
- **Detalhes:**
  - Abertura da tela de edição com os dados atuais do agendamento preenchidos.
  - Atualização do documento no Firestore via `set()`.

### 4.4. Conclusão de Compromisso
- **Descrição:** Marcar um agendamento da equipe como realizado/concluído.
- **Detalhes:**
  - Atualização do campo `status` para `true` no documento correspondente no Firestore.

### 4.5. Exclusão de Agendamento
- **Descrição:** Remoção de um agendamento cancelado ou encerrado.
- **Detalhes:**
  - Exclusão do documento no Firestore via `delete()`.

---

## 5. Soluções Técnicas e Arquitetura

O aplicativo foi projetado seguindo os mais altos padrões de desenvolvimento Android moderno:

### 5.1. Arquitetura e Gerenciamento de Estado (MVVM)
- Utilização do padrão **MVVM (Model-View-ViewModel)** com **`AndroidViewModel`** (`AgendamentoController`).
- Estado reativo através de **`SnapshotStateList`** (`mutableStateListOf`), atuando como um cache em memória sincronizado em tempo real com o Firebase Firestore.

### 5.2. Componentes de Interface (UI) e Design System
- **Jetpack Compose:** Framework declarativo moderno do Android para construção de interfaces fluidas e reativas.
- **Material 3 (M3):** Diretrizes visuais modernas do Material Design, com componentes como `Scaffold`, `Card`, `FloatingActionButton`, `OutlinedTextField`, `OutlinedButton` e `DatePickerDialog`.
- **Navegação com Jetpack Navigation Compose:** Controle de fluxo de telas entre a listagem principal e a tela de formulário com passagem de argumentos (ID do agendamento) e retorno limpo na pilha de navegação via `popBackStack()`.

### 5.3. Persistência de Dados em Nuvem (Firebase Cloud Firestore)
- Armazenamento em nuvem na coleção `"agendamentos"` no **Firebase Cloud Firestore**.
- **Sincronização em Tempo Real (`addSnapshotListener`):** O aplicativo escuta as alterações da coleção em tempo real. Sempre que um documento é inserido, atualizado ou removido por qualquer advogado, o listener atualiza o cache em memória (`SnapshotStateList`) e a interface é recomposta automaticamente para todos os usuários.
- **Tratamento Seguro de Dados:** Conversão de formatos de data (como `Timestamp` do Firebase, `Date` e `String`) garantindo resiliência e prevenção de crashes na inicialização.

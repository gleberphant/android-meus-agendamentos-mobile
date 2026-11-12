# Documento de Requisitos de Produto (PRD) - AppAgendamentos (Agenda Jurídica Mobile)

## 1. Visão Geral do Projeto
O **AppAgendamentos** é um aplicativo Android nativo desenvolvido para uso corporativo em um **escritório de advocacia**. O sistema atua como uma **agenda mobile individual para cada advogado e colaborador**, permitindo o controle rigoroso de prazos, compromissos com clientes, reuniões e diligências diretamente no dispositivo móvel, com armazenamento local seguro e persistente via SQLite.

---

## 2. Justificativa de Criação e Benefícios para a Empresa
A criação desta solução mobile para o escritório justifica-se pelos seguintes pilares estratégicos e operacionais:
- **Mobilidade e Acesso Offline:** Advogados frequentemente transitam entre tribunais, fóruns, clientes e reuniões externas, onde a conexão com a internet pode ser instável. O uso de armazenamento local SQLite (*offline-first*) garante acesso imediato à agenda a qualquer momento, sem dependência de rede.
- **Aumento de Produtividade e Organização:** Centralizar os compromissos diários de cada colaborador evita o esquecimento de prazos processuais cruciais e reuniões com clientes, reduzindo o risco de falhas operacionais e aumentando a eficiência da equipe jurídica.
- **Redução de Custos e Complexidade:** Ao utilizar uma arquitetura local leve baseada no sistema operacional Android nativo, o escritório elimina a necessidade de infraestrutura em nuvem complexa e dispendiosa para esta ferramenta de agenda individual.

---

## 3. Público-Alvo e Usuários
- **Advogados do Escritório:** Profissionais que necessitam consultar e gerenciar seus compromissos e prazos durante deslocamentos e em audiências.
- **Colaboradores e Assistentes Jurídicos:** Equipe de apoio operacional que gerencia o fluxo de agendamentos e reuniões do escritório.

---

## 4. Requisitos Funcionais (Histórias de Usuário)

### 4.1. Listagem de Compromissos e Agendamentos
- **Descrição:** O usuário visualiza todos os seus agendamentos diários na tela principal.
- **Detalhes:**
  - Exibição otimizada em lista (`LazyColumn`).
  - Cada compromisso exibe data formatada (`dd/MM/yyyy`), descrição/assunto jurídico, status (*pendente* ou *concluído*) e opções rápidas de ação (*Editar*, *Concluir*, *Excluir*).

### 4.2. Cadastro de Novo Agendamento
- **Descrição:** O advogado ou colaborador pode registrar um novo compromisso na agenda.
- **Detalhes:**
  - Seleção de data facilitada por um diálogo interativo (*Material 3 DatePicker*).
  - Inserção de descrição detalhada do compromisso ou reunião.
  - Salvamento persistente imediato no banco de dados SQLite.

### 4.3. Edição de Agendamento
- **Descrição:** Atualização de detalhes de um compromisso já agendado.
- **Detalhes:**
  - Abertura da tela de edição pré-populada com os dados do registro selecionado.

### 4.4. Conclusão de Compromisso
- **Descrição:** Marcar um agendamento como realizado/concluído.
- **Detalhes:**
  - Atualização instantânea do status na interface e na base de dados local.

### 4.5. Exclusão de Agendamento
- **Descrição:** Remoção definitiva de um agendamento cancelado ou obsoleto.

---

## 5. Soluções Técnicas e Arquitetura

O aplicativo foi projetado seguindo os mais altos padrões de desenvolvimento Android moderno:

### 5.1. Arquitetura e Gerenciamento de Estado (MVVM)
- Utilização do padrão **MVVM (Model-View-ViewModel)** combinado com **`AndroidViewModel`**.
- Gerenciamento de estado reativo através de `mutableStateListOf` e `mutableStateOf`, garantindo que qualquer alteração nos dados reflita instantaneamente na interface sem necessidade de recarregamento manual.

### 5.2. Componentes de Interface (UI) e Design System
- **Jetpack Compose:** Framework declarativo moderno do Android para construção de interfaces fluidas e reativas.
- **Material 3 (M3):** Adoção das diretrizes visuais do Material 3, incluindo componentes como `Scaffold`, `Card`, `FloatingActionButton`, `OutlinedTextField` e `DatePickerDialog`, proporcionando uma experiência visual moderna, acessível e consistente.
- **Navegação:** Utilização do **Jetpack Navigation Compose** para transições fluidas entre a tela de listagem e a tela de cadastro/edição com suporte a passagem de parâmetros (ID do agendamento).

### 5.3. Persistência de Dados (SQLite Nativo)
- Implementação da classe `AgendamentoDbHelper` estendendo **`SQLiteOpenHelper`** para criar e gerenciar o banco de dados relacional local (`agendamentos.db`).
- Uso de `ContentValues` e `Cursor` para inserção, consulta, atualização e exclusão segura de registros diretamente no armazenamento interno do dispositivo Android.

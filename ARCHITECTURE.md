# Arquitetura do GestureContacts

## Visão Geral

O projeto segue a arquitetura MVVM (Model-View-ViewModel) com Clean Architecture, utilizando as melhores práticas do Android moderno.

## Componentes Principais

### 1. **Gesture Recognition** (`gesture/`)
- **GesturePoint.kt**: Classe que representa um ponto do gesto com coordenadas (x, y) e timestamp
- **GestureRecognizer.kt**: Motor principal de reconhecimento de gestos
  - Normaliza pontos para uma grade 10x10
  - Extrai direções (octantes) entre pontos consecutivos
  - Faz matching contra padrões pré-definidos de letras
  - Usa cálculo de similaridade baseado em DTW (Dynamic Time Warping) simplificado

### 2. **Data Layer** (`db/` e `contacts/`)
- **AppDatabase.kt**: Room Database setup com singleton pattern
- **ContactDao.kt**: Data Access Object com operações CRUD
- **ContactRepository.kt**: Repositório que abstrai acesso a dados
  - Busca contatos do dispositivo via ContentProvider
  - Persiste contatos localmente com Room
  - Fornece fluxo reativo via Flow para mudanças em tempo real

### 3. **UI Layer** (`ui/`)
- **GestureCanvas.kt**: Composable que captura gestos do usuário
  - Detecta arrastar com `detectDragGestures`
  - Desenha em tempo real com `drawWithCache`
  - Chama reconhecedor ao final do gesto

- **GestureSearchScreen.kt**: Tela principal
  - Orquestra Canvas + Resultados de busca
  - Gerencia estado de query e resultados

- **components/ContactListItem.kt**: Item reutilizável da lista de contatos

- **theme/**: Material Design 3 com suporte a cores dinâmicas

### 4. **ViewModel** (`viewmodel/`)
- **GestureSearchViewModel.kt**: Gerencia estado da tela
  - Carrega contatos do dispositivo
  - Filtra contatos baseado em query
  - Expõe dados via StateFlow para Compose

### 5. **Dependency Injection** (`di/`)
- **AppModule.kt**: Hilt module que fornece singletons
  - AppDatabase
  - ContactDao

## Fluxo de Dados

```
Usuário desenha gesto
    ↓
GestureCanvas captura pontos
    ↓
GestureRecognizer.recognize() ← Padrões de letras
    ↓
Char retornado (ex: 'A')
    ↓
ViewModel.searchContacts(char)
    ↓
ContactRepository busca no banco
    ↓
LazyColumn exibe resultados
```

## Permissões Necessárias

```xml
<uses-permission android:name="android.permission.READ_CONTACTS" />
```

**Nota**: No Android 6.0+, é necessário pedir permissão em tempo de execução.

## Padrões de Reconhecimento

Os padrões são baseados em direções (octantes de 0-7):
- `0`: Direita
- `1`: Diagonal inferior-direita
- `2`: Para baixo
- `3`: Diagonal inferior-esquerda
- `4`: Esquerda
- `5`: Diagonal superior-esquerda
- `6`: Para cima
- `7`: Diagonal superior-direita

Exemplo: 'A' = `[2, 2, 2, 4, 0, 4, 0]` (linha vertical + diagonais + horizontal)

## Dependências Principais

- **Jetpack Compose**: UI declarativa
- **Room**: Persistência local
- **Hilt**: Injeção de dependência
- **Coroutines**: Operações assíncronas
- **Material Design 3**: Componentes e temas

## Como Estender

### Adicionar Nova Letra
1. Abra `GestureRecognizer.kt`
2. Adicione um novo padrão em `getLetterPatterns()`
3. Testar desenhando a letra no canvas

### Melhorar Reconhecimento
- Aumentar `normalizePoints()` para mais precisão
- Usar algoritmo DTW mais sofisticado
- Treinar com redes neurais (futuro)

## Performance

- **Canvas**: Desenha apenas com `drawWithCache` para evitar recomposições
- **Banco de Dados**: Room com Flow para atualizações reativas
- **Reconhecimento**: O(n) onde n é número de pontos do gesto

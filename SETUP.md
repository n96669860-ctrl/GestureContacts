## GestureContacts Android App

Um aplicativo Android moderno que funciona como o "Gesture Search" do Google, permitindo buscar contatos desenhando gestos (letras) na tela.

### 📋 Pré-requisitos

- Android Studio 2023.1 ou superior
- JDK 17
- SDK Android 26+
- Emulador ou dispositivo físico com Android 8.0+

### 🚀 Como Executar

1. **Clone o repositório:**
```bash
git clone https://github.com/n96669860-ctrl/GestureContacts.git
cd GestureContacts
```

2. **Abra no Android Studio:**
- File → Open → Selecione a pasta do projeto
- Deixe o Gradle sincronizar automaticamente

3. **Configure seu emulador/dispositivo:**
- Crie um emulador no AVD Manager ou conecte um dispositivo físico via USB
- Certifique-se de ter debug USB habilitado (em dispositivos físicos)

4. **Execute o projeto:**
```bash
./gradlew build
./gradlew installDebug
```

Ou use o botão "Run" do Android Studio (Shift + F10)

### 🎯 Funcionalidades Principais

1. **Canvas de Gestos**: Desenhe uma letra na tela
2. **Reconhecimento**: O app reconhece qual letra você desenhou
3. **Busca Automática**: Filtra seus contatos baseado na letra
4. **Lista de Contatos**: Exibe todos os seus contatos com nome, telefone e email
5. **Material Design 3**: Interface moderna com tema escuro/claro

### 🏗️ Arquitetura

- **MVVM**: Model-View-ViewModel com Jetpack Compose
- **Clean Architecture**: Separação clara entre camadas
- **Room Database**: Persistência local de contatos
- **Hilt**: Injeção de dependência
- **Kotlin Coroutines**: Operações assíncronas

### 📦 Estrutura de Pastas

```
app/src/main/java/com/gesturecontacts/
├── gesture/              # Reconhecimento de gestos
├── contacts/             # Modelo e repositório de contatos
├── db/                   # Room Database
├── ui/                   # Composables e componentes
├── viewmodel/            # ViewModel
├── di/                   # Injeção de dependência
└── GestureContactsApplication.kt
```

### 🔧 Dependências Principais

- Jetpack Compose
- Room Database
- Hilt
- Kotlin Coroutines
- Material Design 3

### ⚙️ Permissões Necessárias

O app solicita:
- `READ_CONTACTS`: Para acessar os contatos do dispositivo
- `INTERNET`: Para futuras expansões

**Nota**: No Android 6.0+, você precisará conceder permissões em tempo de execução.

### 🧪 Testes

```bash
./gradlew test        # Testes unitários
./gradlew connectedAndroidTest  # Testes instrumentados
```

### 🐛 Troubleshooting

**Problema**: Gradle sync falha
- **Solução**: File → Invalidate Caches → Restart

**Problema**: Permissões de contatos negadas
- **Solução**: Vá para Configurações → Apps → GestureContacts → Permissões → Ativar "Contatos"

**Problema**: Canvas não desenha
- **Solução**: Verifique se o evento de toque está sendo detectado corretamente

### 📝 Roadmap

- [x] Estrutura base do projeto
- [x] Reconhecimento de gestos
- [x] Integração com contatos
- [x] UI com Material Design 3
- [ ] Otimizações de reconhecimento
- [ ] Suporte a múltiplos idiomas
- [ ] Testes automatizados
- [ ] Performance improvements

### 📄 Licença

MIT License - veja [LICENSE](LICENSE) para detalhes

### 💡 Dicas de Uso

1. **Desenhe limpo**: Quanto mais claro o seu gesto, melhor o reconhecimento
2. **Tamanho importa**: Quanto maior o gesto, mais fácil reconhecer
3. **Velocidade**: Desenhe em um ritmo normal, nem muito rápido nem muito lento

### 🤝 Contribuindo

Contributions são bem-vindas! Por favor:
1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

### 📧 Contato

Para dúvidas ou sugestões, abra uma issue no repositório.

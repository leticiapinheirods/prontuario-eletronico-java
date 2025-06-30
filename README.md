# Prontuário eletrônico em Java

## ▶️ Como rodar a aplicação

### ✅ Pré-requisito:

Antes de rodar este projeto, certifique-se de que você tem instalado:

- **Java JDK 21 (versão 21 do Java)**
    -  Verifique a instalação (no prompt de comando) com:

    ```
    java --version
    ```

- **IDE com suporte a Java - Para rodar no VScode, certifique-se de ter baixado as seguintes extensões:**
    - Debugger for Java
    - Extension Pack for Java
    - Gradle for Java
    - Language Support for Jav
    - Maven for Java
    - Project Manager for Java
    - Test Runner for Java
    - Live Server

### 🛠️ Passos para rodar a aplicação (no VScode)
**1. Abra o terminal e rode o comando:**
```
./mvnw spring-boot:run
```

**2. No navegador acesse:**
```
http://localhost:8080/api
```
Se aparecer uma lista vazia ( [] ) ou com pacientes, está funcionando!

**3. No diretório principal, percorra as pastas:**

```
 src -> main -> resources -> static -> html 
 ```
 Abra o arquivo ```menu.html``` com o Live Server

**4. Para encerrar a aplicação, digite ```ctrl+c``` no terminal**

### 📂 Configuração de persistência (opcional)

Para garantir que os dados dos pacientes e o código de registro sejam salvos corretamente mesmo após o encerramento da aplicação, é necessário modificar os caminhos de persistência em ```Arquivo.java```.

📝 **Caminhos a serem atualizados**

Abra ```Arquivo.java``` e substitua as linhas abaixo pelo caminho especificado até ```arquivos/pacientes.txt``` e ```arquivos/codigo.txt``` no seu diretório local:

```
private String pathPacientes = "C:/Users/Letic/Downloads/prontuario-eletronico-java/arquivos/pacientes.txt";
private String pathCodigo = "C:/Users/Letic/Downloads/prontuario-eletronico-java/arquivos/codigo.txt";
```


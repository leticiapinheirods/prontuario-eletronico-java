# Prontuário eletrônico em Java

## ▶️ Como rodar a aplicação

### ✅ Pré-requisito:

Antes de rodar este projeto, certifique-se de que você tem instalado:

- Java JDK 21 (versão 21 do Java)
    -  Verifique a instalação (no prompt de comando) com:

    ```
    java --version
    ```

- IDE com suporte a Java - Para rodar no VScode, certifique-se de ter baixado as seguintes extensões:
    - Debugger for Java
    - Extension Pack for Java
    - Gradle for Java
    - Language Support for Jav
    - Maven for Java
    - Project Manager for Java
    - Test Runner for Java
    - Live Server

### 🛠️ Passos para rodar o front + back (no VScode)
**1. Abra o terminal e rode o comando:**
```
./mvnw spring-boot:run
```

**2. No navegador acesse:**
```
http://localhost:8080/api
```
Se aparecer uma lista vazia ( [] ) ou com pacientes, está funcionando!

**3. No diretório principal, percorra as pastas src -> main -> resources -> static -> html e abra menu.html com o Live Server**

**4. Quando quiser parar a aplicação, digite ```ctrl+c``` no terminal**

### 🛠️ Passos para rodar apenas o back (no VScode)

1. No diretório principal, percorra as pastas src -> main -> java\com\integracao\prontuarioeletronico -> br\com\prontuario\eletronico -> Main.java

2. No canto superior direito, selecione a opção "Run Java" para a aplicação rodar no terminal


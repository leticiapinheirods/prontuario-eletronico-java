//Envia os dados do formulário do paciente para a API após validar os campos.
const API_URL = "http://localhost:8080/api"; //Define a URL 

document.addEventListener("DOMContentLoaded", () => { // Aguarda o carregamento da página e seleciona
  const form = document.querySelector("form"); 

  form.addEventListener("submit", async (e) => { //Executa uma função quando o formulário for enviado
    e.preventDefault();
    
    // Captura os valores preenchidos nos campos do formulário
    const nome = document.getElementById("nome").value.trim();
    const idade = parseInt(document.getElementById("idade").value);
    const cpf = document.getElementById("cpf").value.trim();
    const diagnostico = document.querySelector("textarea[name='diagnostico']").value.trim();
    
   // Verifica se os dados são válidos
    if (!nome || isNaN(idade) || idade <= 0 || idade >= 110 || !cpf.match(/^\d{11}$/) || !diagnostico) {
      alert("Por favor, preencha todos os campos corretamente.");
      return; // Interrompe o envio se houver erro
    }
       // Cria um objeto com os dados preenchidos
    const paciente = { nome, idade, cpf, diagnostico };

    try { // Envia os dados para a API usando método POST
      const response = await fetch(API_URL, {
        method: "POST", // Método HTTP para criar novo paciente
        headers: {
          "Content-Type": "application/json", // Envia como JSON
        },
        body: JSON.stringify(paciente),
      });
          //Se o cadastro for bem-sucedido, redireciona para a página de confirmação
      if (response.ok) {
        window.location.href = "../html/cadastromensagem.html";
      } else { //Se houver erro, exibe a mensagem de erro
        const errorText = await response.text();
        alert(`Erro ao cadastrar: ${errorText}`);
      }
    } catch (error) { //Caso ocorra erro na comunicação com a API
      console.error("Erro na requisição:", error);
      alert("Erro ao se conectar com o servidor." + error.message);
    }
  });
});

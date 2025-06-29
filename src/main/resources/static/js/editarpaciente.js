document.addEventListener("DOMContentLoaded", () => {
  const paciente = JSON.parse(localStorage.getItem("pacienteSelecionado"));  //Pega os dados do paciente que estavam guardados no navegador

  if (paciente) { //Se o paciente existir, preenche os campos do formulário com os dados dele
    document.getElementById("nome").value = paciente.nome;
    document.getElementById("idade").value = paciente.idade;
    document.getElementById("cpf").value = paciente.cpf;
    document.getElementsByName("diagnostico")[0].value = paciente.diagnostico;
  }

  const form = document.querySelector("form"); //Seleciona o formulário da página

  form.addEventListener("submit", (e) => { //Adiciona função para rodar quando o formulário for enviado
    e.preventDefault();  //Evita que a página recarregue ao enviar

    const pacienteAtualizado = { //Cria um objeto com os dados atualizados do paciente
      codigo: paciente.codigo, 
      nome: document.getElementById("nome").value,
      idade: parseInt(document.getElementById("idade").value),
      cpf: document.getElementById("cpf").value,
      diagnostico: document.getElementsByName("diagnostico")[0].value
    };
      // Envia os dados atualizados para a API usando o método PUT
    fetch(`http://localhost:8080/api/${paciente.codigo}`, {
      method: "PUT", // Indica que vai atualizar dados existentes
      headers: {
        "Content-Type": "application/json" // Diz que os dados estão em JSON
      },
      body: JSON.stringify(pacienteAtualizado) //Transforma objeto em JSON para enviar
    })
    .then(response => {
      if (response.ok) { // Atualização feita com sucesso, redireciona para a página de confirmação
        window.location.href = "../html/atualizarmensagem.html";
      } else { // Se houve algum problema na atualização, mostra uma mensagem de erro
        alert("Erro ao atualizar paciente.");
      }
    })
    .catch(error => { //Se falhar ao conectar com o servidor, mostra mensagem de erro
      console.error("Erro na requisição:", error);
      alert("Erro de conexão com o servidor.");
    });
  });
});

document.addEventListener("DOMContentLoaded", () => {
  const paciente = JSON.parse(localStorage.getItem("pacienteSelecionado"));

  if (paciente) {
    document.getElementById("nome").value = paciente.nome;
    document.getElementById("idade").value = paciente.idade;
    document.getElementById("cpf").value = paciente.cpf;
    document.getElementsByName("diagnostico")[0].value = paciente.diagnostico;
  }

  const form = document.querySelector("form");

  form.addEventListener("submit", (e) => {
    e.preventDefault(); 

    const pacienteAtualizado = {
      codigo: paciente.codigo, 
      nome: document.getElementById("nome").value,
      idade: parseInt(document.getElementById("idade").value),
      cpf: document.getElementById("cpf").value,
      diagnostico: document.getElementsByName("diagnostico")[0].value
    };

    fetch(`http://localhost:8080/api/${paciente.codigo}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(pacienteAtualizado)
    })
    .then(response => {
      if (response.ok) {
        window.location.href = "../html/atualizarmensagem.html";
      } else {
        alert("Erro ao atualizar paciente.");
      }
    })
    .catch(error => {
      console.error("Erro na requisição:", error);
      alert("Erro de conexão com o servidor.");
    });
  });
});

document.addEventListener("DOMContentLoaded", () => {
  const [btnSim, btnNao] = document.querySelectorAll("button"); //Seleciona os dois botões da página Sim e Não

  btnSim.addEventListener("click", () => { // Se clicar no botão "Sim", confirmar exclusão
    const paciente = JSON.parse(localStorage.getItem("pacienteSelecionado")); // Pega os dados do paciente armazenados no navegador

    if (paciente && paciente.codigo) { //Se o paciente existir e tiver código
      fetch(`http://localhost:8080/api/${paciente.codigo}`, { //Envia pedido para o servidor excluir esse paciente
        method: "DELETE"
      })
      .then(response => { 
        if (response.ok) { // Se deu certo, remove o paciente do armazenamento local
          localStorage.removeItem("pacienteSelecionado");
          window.location.href = "../html/excluirmensagem.html";  // Redireciona para a página que mostra mensagem de exclusão feita
        } else { // Se houve erro ao excluir, exibe uma mensagem de alerta para o usuário
          alert("Erro ao excluir paciente."); 
        }
      })
      .catch(error => { //Se não conseguir se conectar com o servidor, mostra erro
        console.error("Erro:", error);
        alert("Erro na requisição." + error.message);
      });
    }
  });
     //Se clicar no botão "Não" (cancelar exclusão) e volta para a página de informações do paciente
  btnNao.addEventListener("click", () => {
    window.location.href = "../html/informpaciente.html";
  });
});

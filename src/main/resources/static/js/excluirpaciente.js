document.addEventListener("DOMContentLoaded", () => {
  const [btnSim, btnNao] = document.querySelectorAll("button");

  btnSim.addEventListener("click", () => {
    const paciente = JSON.parse(localStorage.getItem("pacienteSelecionado"));

    if (paciente && paciente.codigo) {
      fetch(`http://localhost:8080/api/${paciente.codigo}`, {
        method: "DELETE"
      })
      .then(response => {
        if (response.ok) {
          localStorage.removeItem("pacienteSelecionado");
          window.location.href = "../html/excluirmensagem.html";
        } else {
          alert("Erro ao excluir paciente.");
        }
      })
      .catch(error => {
        console.error("Erro:", error);
        alert("Erro na requisição." + error.message);
      });
    }
  });

  btnNao.addEventListener("click", () => {
    window.location.href = "../html/informpaciente.html";
  });
});

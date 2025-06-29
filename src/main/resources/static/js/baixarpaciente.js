document.addEventListener("DOMContentLoaded", () => {
  const paciente = JSON.parse(localStorage.getItem("pacienteSelecionado"));

  if (paciente) {
    const btnBaixar = document.querySelector("button[title='Baixar']");

    btnBaixar.addEventListener("click", () => {
      const link = document.createElement("a");
      link.href = `http://localhost:8080/api/baixar/${paciente.codigo}`;
      link.download = `paciente_${paciente.codigo}.txt`;
      document.body.appendChild(link); 
      link.click();
      document.body.removeChild(link); 

      setTimeout(() => {
        window.location.href = "../html/consultardowload.html";
      }, 1000);
    });
  }
});

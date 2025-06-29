document.addEventListener("DOMContentLoaded", () => { //Aguarda o carregamento completo do conteúdo da página
  const paciente = JSON.parse(localStorage.getItem("pacienteSelecionado")); //Pega os dados do paciente que foram salvos no localStorage e converte de string para objeto
  
 //Faz o download do arquivo do paciente ao clicar no botão
  if (paciente) { //Verifica se existe um paciente selecionado
    const btnBaixar = document.querySelector("button[title='Baixar']"); // Seleciona o botão "Baixar"

    btnBaixar.addEventListener("click", () => {  // Ao clicar no botão
      const link = document.createElement("a");  // Cria link para download do arquivo do paciente
      link.href = `http://localhost:8080/api/baixar/${paciente.codigo}`;  
      link.download = `paciente_${paciente.codigo}.txt`;               
      document.body.appendChild(link);  // Adiciona e clica no link para iniciar o download
      link.click(); 
      document.body.removeChild(link);   // Remove o link após o clique

      setTimeout(() => {  // Depois de 1 segundo, redireciona o usuário para outra página
        window.location.href = "../html/consultardowload.html";
      }, 1000);
    });
  }
});

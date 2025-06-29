const API_URL = "http://localhost:8080/api";

document.addEventListener("DOMContentLoaded", () => {
  const lista = document.getElementById("listaPacientes");  // Seleciona o elemento da lista onde os pacientes serão mostrados
  const searchInput = document.getElementById("searchInput"); // Seleciona o campo de busca

  let pacientes = []; //Array para guardar os pacientes carregados
  
 //Função que carrega os pacientes da API
  async function carregarPacientes() {  
    try {
      const response = await fetch(API_URL); // Faz uma requisição GET para a API
      if (response.ok) {
        pacientes = await response.json(); //Converte os dados recebidos em JSON
        renderizarLista(pacientes);// Mostra a lista completa na tela
      } else {
        console.error("Erro ao buscar pacientes");
      }
    } catch (error) {
      console.error("Erro na conexão:", error);
    }
  }
// Função que mostra a lista de pacientes no HTML
  function renderizarLista(listaPacientes) {
    lista.innerHTML = "";
    listaPacientes.forEach(paciente => { // Percorre todos os pacientes recebidos
      const li = document.createElement("li"); // Cria um item de lista
      li.textContent = `${paciente.codigo} - ${paciente.nome}`;
      li.classList.add("item-paciente"); 
      li.addEventListener("click", () => { //Quando clicar no paciente, salva no localStorage e vai para a página de detalhes
        localStorage.setItem("pacienteSelecionado", JSON.stringify(paciente));
        window.location.href = "../html/informpaciente.html";
      });
      lista.appendChild(li); // Insere o paciente na lista
    });
  }
 //Filtra a lista conforme o usuário digita no campo de busca
  searchInput.addEventListener("input", () => {
    const termo = searchInput.value.toLowerCase();
    const filtrados = pacientes.filter(p => //Busca paciente pelo nome ou código que contenha o termo digitado
      p.nome.toLowerCase().includes(termo) || String(p.codigo).includes(termo)
    );
    renderizarLista(filtrados); //Exibe apenas os pacientes que correspondem ao que foi digitado na busca
  });
         //Carrega a lista de pacientes assim que a página abre
  carregarPacientes();
});

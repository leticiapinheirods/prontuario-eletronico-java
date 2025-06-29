const API_URL = "http://localhost:8080/api";

document.addEventListener("DOMContentLoaded", () => {
  const lista = document.getElementById("listaPacientes");
  const searchInput = document.getElementById("searchInput");

  let pacientes = [];

  async function carregarPacientes() {
    try {
      const response = await fetch(API_URL);
      if (response.ok) {
        pacientes = await response.json();
        renderizarLista(pacientes);
      } else {
        console.error("Erro ao buscar pacientes");
      }
    } catch (error) {
      console.error("Erro na conexão:", error);
    }
  }

  function renderizarLista(listaPacientes) {
    lista.innerHTML = "";
    listaPacientes.forEach(paciente => {
      const li = document.createElement("li");
      li.textContent = `${paciente.codigo} - ${paciente.nome}`;
      li.classList.add("item-paciente");
      li.addEventListener("click", () => {
        localStorage.setItem("pacienteSelecionado", JSON.stringify(paciente));
        window.location.href = "../html/informpaciente.html";
      });
      lista.appendChild(li);
    });
  }

  searchInput.addEventListener("input", () => {
    const termo = searchInput.value.toLowerCase();
    const filtrados = pacientes.filter(p => 
      p.nome.toLowerCase().includes(termo) || String(p.codigo).includes(termo)
    );
    renderizarLista(filtrados);
  });

  carregarPacientes();
});

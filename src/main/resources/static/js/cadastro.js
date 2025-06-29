const API_URL = "http://localhost:8080/api";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const nome = document.getElementById("nome").value.trim();
    const idade = parseInt(document.getElementById("idade").value);
    const cpf = document.getElementById("cpf").value.trim();
    const diagnostico = document.querySelector("textarea[name='diagnostico']").value.trim();

    if (!nome || isNaN(idade) || idade <= 0 || idade >= 110 || !cpf.match(/^\d{11}$/) || !diagnostico) {
      alert("Por favor, preencha todos os campos corretamente.");
      return;
    }

    const paciente = { nome, idade, cpf, diagnostico };

    try {
      const response = await fetch(API_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(paciente),
      });

      if (response.ok) {
        window.location.href = "../html/cadastromensagem.html";
      } else {
        const errorText = await response.text();
        alert(`Erro ao cadastrar: ${errorText}`);
      }
    } catch (error) {
      console.error("Erro na requisição:", error);
      alert("Erro ao se conectar com o servidor." + error.message);
    }
  });
});

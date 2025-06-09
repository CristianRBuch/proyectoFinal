async function cargarEstudiantes() {
  const res = await fetch("http://localhost:8080/students");
  const estudiantes = await res.json();
  const tbody = document.querySelector("#usuarios tbody");
  tbody.innerHTML = "";

  estudiantes.forEach(est => {
    tbody.innerHTML += `
      <tr>
        <td>${est.id}</td>
        <td>${est.nombre}</td>
        <td>${est.apellido}</td>
        <td>${est.email}</td>
        <td>${est.telefono}</td>
        <td>${est.idioma}</td>
        <td>
          <button onclick="verEstudiante(${est.id})" class="btn btn-info btn-sm me-1">Ver</button>
          <button onclick="editarEstudianteCompleto(${est.id})" class="btn btn-primary btn-sm me-1">Editar</button>
          <button onclick="editarEstudianteParcial(${est.id})" class="btn btn-warning btn-sm me-1">Editar Parcial</button>
          <button onclick="eliminarEstudiante(${est.id})" class="btn btn-danger btn-sm">Eliminar</button>
        </td>
      </tr>
    `;
  });
}

async function verEstudiante(id) {
  window.location.href = `ver-estudiante.html?id=${id}`;
}

async function editarEstudianteCompleto(id) {
  window.location.href = `editar-estudiante.html?id=${id}`;
}

async function editarEstudianteParcial(id) {
  window.location.href = `editar-contacto.html?id=${id}`;
}

async function eliminarEstudiante(id) {
  if (!confirm("¿Desea eliminar este estudiante?")) return;

  await fetch(`http://localhost:8080/students/${id}`, {
    method: "DELETE"
  });

  cargarEstudiantes();
}

document.getElementById("form-estudiante").addEventListener("submit", async function (e) {
  e.preventDefault();
  const form = e.target;

  const estudiante = {
    nombre: form.nombre.value,
    apellido: form.apellido.value,
    email: form.email.value,
    telefono: form.telefono.value,
    idioma: form.idioma.value
  };

  try {
    const response = await fetch("http://localhost:8080/students", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(estudiante)
    });

    if (!response.ok) throw await response.json();

    alert("Estudiante guardado correctamente");
    form.reset();
    cargarEstudiantes();
  } catch (err) {
    alert("Error al guardar estudiante: " + JSON.stringify(err));
  }
});

document.addEventListener("DOMContentLoaded", cargarEstudiantes);

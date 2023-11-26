const { createApp } = Vue;

createApp({
  methods:{
    redireccionarlogin: function() {
        // Redirigir a la URL especificada
        window.location.href = "./pages/login.html"; // Reemplaza con la URL a la que deseas redirigir
    },
    redireccionarsingup: function() {
        // Redirigir a la URL especificada
        window.location.href = "./pages/register.html"; // Reemplaza con la URL a la que deseas redirigir
    },
    
  }
}).mount("#app");
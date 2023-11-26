let { createApp } = Vue;

createApp({
  data() {
    return {
        email: "",
        password: ""
    };
  },

  created(){
    
  },

  methods:{
    redireccionar: function() {
      // Redirigir a la URL especificada
      window.location.href = "../Index.html"; // Reemplaza con la URL a la que deseas redirigir
  },
    login(){
        axios.post('/api/login', `email=${this.email}&password=${this.password}`)
        .then( response => {
            if(this.email == "melbamorel98@gmail.com"){
              location.pathname="/web/pages/loan-admin.html"
            }
            else{
              location.pathname="/web/pages/accounts.html"
            }
            console.log(response)
        })
        .catch(err => {Swal.fire({
          title: "Error",
          text: err.response.data,
          icon: "error"
        })})
    },
  },
}).mount("#app");
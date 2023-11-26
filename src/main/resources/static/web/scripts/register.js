let { createApp } = Vue;

createApp({
    data() {
        return {
            firstName: "",
            lastName: "",
            email: "",
            password: ""
        };
    },

    created() {

    },

    methods: {
        redireccionar: function() {
            // Redirigir a la URL especificada
            window.location.href = "../Index.html"; // Reemplaza con la URL a la que deseas redirigir
        },
        register() {
            axios.post("/api/clients", `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`)
                .then((response) => {
                     console.log("User registered");
                     axios.post('/api/login', `email=${this.email}&password=${this.password}`)
                         .then(response => {
                            console.log(response)
                            location.pathname = "/web/pages/accounts.html"
                         })
                         .catch(err => {Swal.fire({
                            title: "Error",
                            text: err.response.data,
                            icon: "error"
                          })})
                })
                .catch((error) => {Swal.fire({
                    title: "Error",
                    text: err.response.data,
                    icon: "error"
                  })});
        },
    }
}).mount("#app");
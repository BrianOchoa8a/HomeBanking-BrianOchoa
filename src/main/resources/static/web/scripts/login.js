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
    login(){
        axios.post('/api/login', `email=${this.email}&password=${this.password}`)
        .then( response => {
            console.log(response)
            location.pathname="/web/pages/accounts.html"
        })
        .catch(err => {Swal.fire({
          title: "Error",
          text: err.response.data,
          icon: "error"
        })})
    },
  },
}).mount("#app");
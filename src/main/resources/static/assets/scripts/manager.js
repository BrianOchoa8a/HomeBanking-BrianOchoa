const { createApp } = Vue;

createApp({
  data() {
    return {
        clients: [],
        clientRest: "",
        firstName:"",
        lastName:"",
        email:""

    };
  },
  created(){
    this.getData();
  },
  methods:{
    getData(){
        axios('http://localhost:8080/clients')
        .then(({data}) => {
            this.clients = data._embedded.clients;
            console.log(this.clients)
            this.clientRest = data;
        })
        .catch(err => console.log(err))
    },
    loadData(){
        axios.post('http://localhost:8080/clients', {
            firstName: this.firstName,
            lastName: this.lastName,
            email: this.email
        })
        window.location.reload();
    }
  },
}).mount("#app");
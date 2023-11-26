const { createApp } = Vue;

createApp({
  data() {
    return {
      id:"",
      account: [],
      client: []
    };
  },
  created(){
    this.id =  new URLSearchParams(location.search).get("id");
    this.loadAccount();
  },
  methods:{

    back: function() {
      // Redirigir a la URL especificada
      window.location.href = "./accounts.html"; // Reemplaza con la URL a la que deseas redirigir
  },

    loadAccount(){
        axios.get("/api/clients/current")
        .then(({data}) => {
          this.client = data;
          this.account = this.client.accounts.find(a => a.id == this.id);
          this.account.transactions.sort((a, b) => b.id - a.id);
        })
        .catch(err => console.log(err))
    },
    dateFormat(date) {
      return moment(date).format('lll');
    },
    logout(){
      axios.post('/api/logout')
      .then( response => {
        location.pathname="/web/index.html"
      })
    }
  }
}).mount("#app");
let { createApp } = Vue;

createApp({
  data() {
    return {
        accounts: [],
        name:"",
        loans:[],
        accountType:""
    };
  },

  created(){
    this.loadData();
  },

  methods:{
    redireccionar2: function() {
      // Redirigir a la URL especificada
      window.location.href = "../Index.html"; // Reemplaza con la URL a la que deseas redirigir
  },
    loadData(){
        axios.get('/api/clients/current')
        .then( ({data}) => {
          console.log(data);
             this.accounts = data.accounts;
            this.name = data.firstName;
             this.loans = data.loans;
        })
        .catch(err => console.log(err))
    },
    deleteAccoun(number){
      Swal.fire({
          title: 'Are you sure to delete this card?',
          text: 'This action cannot be reversed',
          showCancelButton: true,
          cancelButtonText: 'Cancell',
          confirmButtonText: 'Yes',
          confirmButtonColor: '#28a745',
          cancelButtonColor: '#dc3545',
          showClass: {
            popup: 'swal2-noanimation',
            backdrop: 'swal2-noanimation'
          },
          hideClass: {
            popup: '',
            backdrop: ''
      }, preConfirm: () => {
      axios.patch(`/api/account/modify`, `number=${number}`)
          .then(() => {
              Swal.fire({
                  icon: 'success',
                  text: 'Your account was deleted',
                  showConfirmButton: false,
                  timer: 2000,
              })
              this.loadData();
          })
          .catch(error => {
              Swal.fire({
                icon: 'error',
                text: error.response.data,
                confirmButtonColor: "#7c601893",
              });
      });
      },
  })
  },
    createAccount() {
      axios.post('/api/clients/current/accounts', `accountType=${this.accountType}`)
      .then( response => {
        this.loadData();
      })
      .catch(err => console.log(err))
    },

    logout(){
      axios.post('/api/logout')
      .then( response => {
        location.pathname="/web/Index.html"
      })      
      .catch(err => console.log(err))
    },
  },
}).mount("#app");
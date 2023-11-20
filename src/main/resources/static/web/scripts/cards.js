let { createApp } = Vue;

createApp({
  data() {
    return {
        credit: [],
        debit: [],
        currentDate: new Date(),
    };
  },

  created(){
    this.loadData();
  },

  methods:{
    loadData(){
        axios.get('/api/clients/current')
        .then( ({data}) => {
            this.debit = data.cards.filter(card => card.type == "DEBIT");
            this.credit = data.cards.filter(card => card.type == "CREDIT");
        })
        .catch(err => console.log(err))
    },
    cardExpirationDate(date) {
      return new Date(date) < this.currentDate
  },
    dateFormat(date) {

      return (date).format("MM/YY");

    },
    deleteCard(id){
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
      axios.patch(`/api/cards/modify`, `id=${id}`)
          .then(() => {
              Swal.fire({
                  icon: 'success',
                  text: 'Your Card was deleted',
                  showConfirmButton: false,
                  timer: 2000,
              })
              location.pathname = `/web/pages/cards.html`;
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
    logout(){
      axios.post('/api/logout')
      .then( response => {
        location.pathname="/web/index.html"
      })
    }
  },
}).mount("#app");
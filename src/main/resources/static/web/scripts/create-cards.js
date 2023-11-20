let { createApp } = Vue;

createApp({
  data() {
    return {
        typeCard: "",
        colorCard: ""
    };
  },

  created(){
  },

  methods:{
    createCard(){
        axios.post('/api/clients/current/cards', `cardType=${this.typeCard}&cardColor=${this.colorCard}`)
        .then((response) => {
          Swal.fire({
              title: "Are you sure?",
              text: "You won't be able to revert this!",
              icon: "warning",
              showCancelButton: true,
              confirmButtonColor: "#3085d6",
              cancelButtonColor: "#d33",
              confirmButtonText: "Yes, Create it!"
            }).then((result) => {
              Swal.fire({
                  title: "Your Card was approved",
                  icon: "success",
                  confirmButtonColor: "#3085d6",
                }).then((result) => {
                  if (result.isConfirmed) {
                      location.pathname = "/web/pages/create-cards.html";
                  }
                });
              }
            )
    })

        .catch(error => {
          Swal.fire({
            icon: 'error',
            text: error.response.data,
            confirmButtonColor: "#7c601893", 
          })});
    },
    logout(){
      axios.post('/api/logout')
      .then( response => {
        location.pathname="/web/index.html"
      })
    }
  },
}).mount("#app");
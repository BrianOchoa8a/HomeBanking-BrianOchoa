let { createApp } = Vue;

createApp({
  data() {
    return {
      cardCredit:[],
      cardDebit:[]
    };
  },

  created(){
    this.loadData();
  },

  methods:{
    loadData(){
        axios.get('/api/clients/1')
        .then( ({data}) => {
          console.log(data);
          
          this.cardCredit = data.cards.filter(card => card.type == "CREDIT");
          console.log(this.cardCredit);
          this.cardDebit = data.cards.filter(card2 => card2.type == "DEBIT");
          console.log(this.cardDebit);
        })
        .catch(err => console.log(err))
    },
  },
}).mount("#app");
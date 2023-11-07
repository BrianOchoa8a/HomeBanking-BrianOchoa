let { createApp } = Vue;

createApp({
    data() {
        return {
            loans: [],
            numberAccount: "",
            amount:"",
            payments: ""
        };
    },
    created() {
        axios.get("/api/loans")
            .then((response) => {
                console.log("mm");
                console.log(response);
            })
    },
    methods: {
        newLoan() {
            console.log(this.numberAccount, this.amount, this.payment)
            axios.post("/api/transactions", `numberAccount=${this.numberAccount}&amount=${this.amount}&payments=${this.payments}`)
                .then((response) => {
                    console.log(response)
                }).catch((err) => console.log(err))
        }
    },
}).mount('#app');
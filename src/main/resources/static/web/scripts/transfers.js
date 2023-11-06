let { createApp } = Vue;

createApp({
    data() {
        return {
            accountsOwn: [],
            selectedFrom: "",
            selectedTo: "",
            transferType: "",
            accountDestination: "",
            amount: "",
            description: "",
            selectAccount:""

        };
    },
    created() {
        axios.get("/api/clients/current/accounts")
            .then((response) => {
                console.log("mm")
                this.accountsOwn = response.data.map(account => account.number)
            })
    },
    methods: {
        newTransaction() {
            console.log(this.amount, this.selectedFrom, this.accountDestination, this.description, this.accountsOwn)
            axios.post("/api/transactions", `amount=${this.amount}&description=${this.description}&originAccount=${this.selectedFrom}&destinationAccount=${this.accountDestination}`)
                .then((response) => {
                    console.log(response)
                }).catch((err) => console.log(err))
        }
    },
}).mount('#app');
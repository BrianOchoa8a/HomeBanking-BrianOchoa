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
                this.accountsOwn = response.data.map(account => account.number)
            })
    },
    methods: {
        newTransaction() {
            console.log(this.amount, this.description, this.selectedFrom, this.accountDestination, this.accountsOwn)
            axios.post("/api/clients/current/transactions", `amount=${this.amount}&description=${this.description}&originAccount=${this.selectedFrom}&destinationAccount=${this.accountDestination}`)
                .then((response) => {Swal.fire({
                    title: "Your Transfer was approved",
                    icon: "success",
                    confirmButtonColor: "#3085d6",
                  }).then((result) => {
                    if (result.isConfirmed) {
                        location.pathname = "/WEB/pages/transfers.html";
                    }
                  });
                }).catch((err) => {Swal.fire({
                    title: "Error",
                    text: err.response.data,
                    icon: "error"
                  })})
        }
    },
}).mount('#app');
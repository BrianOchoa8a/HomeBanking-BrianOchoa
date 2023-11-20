let { createApp } = Vue;

createApp({
    data() {
        return {
            loansAvailable: [],
            accountsOwn: [],
            TypeLoan: "",
            amount: "",
            accountTransfer: "",
            payments: "",
            loansAvailableCoutas: [],
            calculateQuota: "",
            advertencia: "",
        };
    },
    created() {

    },
    methods: {
        loans() {
            axios.get("/api/loans")
                .then((response) => {
                    this.loansAvailable = response.data

                }).catch(err => {Swal.fire({
                    title: "Error",
                    text: err.response.data,
                    icon: "error"
                  })})

        },
        accounts() {
            axios.get("/api/clients/current/accounts")
                .then((response) => {
                    this.accountsOwn = response.data.filter(accounts => accounts.active==true).map(account => account.number)
                }).catch(err => {Swal.fire({
                    title: "Error",
                    text: err.response.data,
                    icon: "error"
                  })})
        },
        newloan() {
            axios.post("/api/loans", {
                id: this.TypeLoan.id,
                amount: this.amount,
                payments: this.payments,
                numberAccount: this.accountTransfer,

            }
            ).then((response) => {
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
                        title: "Your Loan was approved",
                        icon: "success",
                        confirmButtonColor: "#3085d6",
                      }).then((result) => {
                        if (result.isConfirmed) {
                            location.pathname = "/web/pages/loan-application.html";
                        }
                      });
                    }
                  )

            })  .catch(error => {
                Swal.fire({
                  icon: 'error',
                  text: error.response.data,
                  confirmButtonColor: "#7c601893", 
                })});
            }
            },

    computed: {
        metodo() {
            console.log(this.TypeLoan)
            console.log(this.TypeLoan.id, this.amount, this.payments, this.accountTransfer)
        },
        typeloan() {
            this.loansAvailableCoutas = this.TypeLoan.payments

        },
        capturarValorCuota() {
            if (this.amount && this.payments) {
                let valorAmount = parseFloat(this.amount)
                this.calculateQuota = (valorAmount + (valorAmount * 0.20)) / this.payments;
            } else {
                this.calculateQuota = ""
            }
        },
        maxAmount() {
            if (this.amount > this.TypeLoan.maxAmount) {
                this.advertencia =`El monto maximo disponible a solicitar es ${this.TypeLoan.maxAmount}`
                console.log(this.advertencia)
            } else {
                this.advertencia = ""
            }

        }
    }
}).mount('#app');
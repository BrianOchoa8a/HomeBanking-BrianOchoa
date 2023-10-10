let { createApp } = Vue;

createApp({
    data() {
        return {
            id:"",
            account:[],
            transactions:[]

        };
    },
    created() {
        this.id = new URLSearchParams(location.search).get("id");
        console.log(this.id);
         this.loadData(this.id);
        console.log(this.account);
    },
    methods: {
        loadData(id) {
            axios.get(`/api/accounts/${id}`)
                .then(({data}) => {
                    console.log(data);
                    this.account=data;
                })
                .catch(err => console.log(err));
        },

    },
}).mount('#app');
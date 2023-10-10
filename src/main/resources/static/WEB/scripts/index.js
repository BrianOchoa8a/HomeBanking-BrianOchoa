let { createApp } = Vue;

createApp({
    data() {
        return {
            name:""
        };
    },
    created() {
        this.loadData();
    },
    methods: {
        loadData() {
            axios.get("/api/clients/1")
                .then(({data}) => {
                    console.log(data);
                    this.name = data.firstName;
                })
                .catch((err) => console.log(err));
        },

    },
}).mount('#app');
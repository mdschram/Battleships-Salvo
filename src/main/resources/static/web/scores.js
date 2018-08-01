var scorevue = new Vue({
    el: '#scorevue',
    data: {scoreData: []
        
    },
    methods: {
        getDataObject: function (gamePlayer) {
            var fetchConfig =
                fetch("/api/scoreboard/", {
                    method: "GET",
                    credential: "include"
                }).then(this.onDataFetched)
        },
        onConversionToJsonSuccessful: function (json) {
            scorevue.scoreData = json;
            console.log(this.scoreData)
           
        },
        onDataFetched: function (response) {
            response.json()
                .then(this.onConversionToJsonSuccessful)
        }
    },
    created: function(){
        this.getDataObject()
    }
})

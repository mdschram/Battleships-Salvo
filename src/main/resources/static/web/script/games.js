var main = new Vue({
    el: '#main',
    data: {
        gameData: [],
        gamesList: [],
        game: {
            date: "",
            player1: "",
            player2: ""
        },
        date: 0
    },
    methods: {
        getDataObject: function () {
            var fetchConfig =
                fetch("/api/games", {
                    method: "GET",
                    credential: "include"
                }).then(this.onDataFetched)
        },
        onConversionToJsonSuccessful: function (json) {
            main.gameData = json;
            console.log(this.gameData)
            this.createGameList();
        },
        onDataFetched: function (response) {
            response.json()
                .then(main.onConversionToJsonSuccessful)
        },
        createGameList: function () {
            for (i = 0; i < this.gameData.length; i++) {
                this.game = {
                    date: "",
                    player1: "",
                    player2: ""
                }
                this.date = new Date(this.gameData[i].created);
                this.game.date = this.date.toLocaleString()
                this.game.player1 = this.gameData[i].gameplayers[0].player.username
                if (this.gameData[i].gameplayers.length > 1) {
                    this.game.player2 = this.gameData[i].gameplayers[1].player.username
                } else this.gameplayer = "";
                this.gamesList.push(this.game)


            }
            console.log("hoi")
            console.log(this.gamesList)
            var date = Date()
            console.log(date)
            //            console.log(this.gameData[1].gameplayers["0"].player.username["0"].created)


        }
    },

    created: function () {
        this.getDataObject()
    }
})

//console.log("js loaded")["0"].gameplayers["0"].player.username["0"].created

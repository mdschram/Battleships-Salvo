var main = new Vue({
    el: '#main',
    data: {
        rows: ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
        columns: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
        gameDate: {},
        shipLocations: [],
        usernames: [],
    },
    methods: {
        getDataObject: function (gamePlayer) {
            var fetchConfig =
                fetch("/api/game_view/" + gamePlayer, {
                    method: "GET",
                    credential: "include"
                }).then(this.onDataFetched)
        },
        onConversionToJsonSuccessful: function (json) {
            main.gameData = json;
            console.log(this.gameData)
            this.getShips()
            this.getPlayers()
            this.getSalvoes(this.gameData.usersalvoes, "salvo ", "green")
            this.getSalvoes(this.gameData.enemysalvoes, "ship ", "orange")
        },
        onDataFetched: function (response) {
            response.json()
                .then(main.onConversionToJsonSuccessful)
        },
        paramObj: function (search) {
            var obj = {};
            var reg = /(?:[?&]([^?&#=]+)(?:=([^&#]*))?)(?:#.*)?/g;
            search.replace(reg, function (match, param, val) {
                obj[decodeURIComponent(param)] = val === undefined ? "" : decodeURIComponent(val);
            });
            return obj;
        },
        getShips: function () {
            for (i = 0; i < this.gameData.ships.length; i++) {
                for (j = 0; j < this.gameData.ships[i].location.length; j++) {
                    this.shipLocations.push(this.gameData.ships[i].location[j])
                }
            }
            for (i = 0; i < this.shipLocations.length; i++) {
                document.getElementById("ship " + this.shipLocations[i]).style.backgroundColor = "red"
            }
        },
        getSalvoes: function (salvotype, grid, color) {
            for (i = 0; i < salvotype.length; i++) {
                for (j = 0; j < salvotype[i].location.length; j++) {
                    let shot = salvotype[i].location[j];
                    if (shot != "" && salvotype == this.gameData.usersalvoes) {
                        document.getElementById(grid + shot).style.backgroundColor = color
                        document.getElementById(grid + shot).innerHTML = salvotype[i].turn
                    } else if (shot != "" && this.shipLocations.includes(shot)) {
                        document.getElementById(grid + shot).style.backgroundColor = color
                        document.getElementById(grid + shot).innerHTML = salvotype[i].turn
                    }
                }
            }
        },
        getPlayers: function () {
            let players = this.gameData.game.gameplayers
            for (i = 0; i < players.length; i++) {
                if (players[i].id == this.gamePlayer.gp) {
                    this.usernames.push(players[i].player.username + " (You)")
                } else {
                    this.usernames.push(players[i].player.username)
                }
            }
        }
    },
    created: function () {
        this.gamePlayer = this.paramObj(location.search)
        this.getDataObject(this.gamePlayer.gp)
    }
})



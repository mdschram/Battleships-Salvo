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
        paramObj: function () {
            var url = location.search;
            var x = url.split('=')[1];
            this.gamePlayer = x;
            return this.gamePlayer
        },
        getShips: function () {
            for (i = 0; i < this.gameData.ships.length; i++) {
                for (j = 0; j < this.gameData.ships[i].location.length; j++) {
                    this.shipLocations.push(this.gameData.ships[i].location[j])
                }
            }
            for (i = 0; i < this.shipLocations.length; i++) {
                document.getElementById("ship " + this.shipLocations[i]).style.backgroundColor = "yellow"
            }
        },
        getSalvoes: function (salvotype, grid, color) {
            for (i = 0; i < salvotype.length; i++) {
                for (j = 0; j < salvotype[i].location.length; j++) {
                    let shot = salvotype[i].location[j];
                    let cell = document.getElementById(grid + shot);
                    if (shot != "" && salvotype == this.gameData.usersalvoes) {
                        cell.style.backgroundColor = color;
                        cell.innerHTML = salvotype[i].turn;
                    } else if (shot != "") {
                        cell.style.backgroundColor = color;
                        cell.innerHTML = salvotype[i].turn;
                    };
                    if (this.shipLocations.includes(shot) && salvotype == this.gameData.enemysalvoes) {
                        cell.style.backgroundColor = "red";
                    }
                }
            }
        },
        getPlayers: function () {
            let players = this.gameData.game.gameplayers
            for (i = 0; i < players.length; i++) {
                if (players[i].id == this.id) {
                    this.usernames.push(players[i].player.username + " (You)")
                } else {
                    this.usernames.push(players[i].player.username)
                }
            }
        },
        logout: function () {
           fetch("/api/logout")
                .then(window.location.href = '/web/games.html')
                .catch();
        }
    },
    created: function () {
        this.getDataObject(this.paramObj());
    }
})

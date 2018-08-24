var main = new Vue({
    el: '#main',
    data: {
        scoreData: [],
        gameData: [],
        gamesList: [],
        game: {
            date: "",
            player1: "",
            player2: ""
        },
        date: 0,
        userLoggedIn: false,
        createPlayerMessage: "",
        currentUser: "" 
    },
    methods: {
        getScores: function (gamePlayer) {
            var fetchConfig =
                fetch("/api/scoreboard/", {
                    method: "GET",
                    credential: "include"
                }).then(this.onScoreDataFetched)
        },
        onScoreConversionToJsonSuccessful: function (json) {
            this.scoreData = json;
        },
        onScoreDataFetched: function (response) {
            response.json()
                .then(this.onScoreConversionToJsonSuccessful)
        },
        getDataObject: function () {
            var fetchConfig =
                fetch("/api/games", {
                    method: "GET",
                    credentials: "include"
                }).then(this.onDataFetched)
        },
        onConversionToJsonSuccessful: function (json) {
            document.getElementById("gameslist").innerHTML = ""
            main.gameData = json;
            console.log(this.gameData)
            if (this.gameData.player != "no player logged in") {
                this.userLoggedIn = true
            } else {
                this.userLoggedIn = false
            }
            this.createGameList();
            this.currentUser = this.gameData.player.username
        },
        onDataFetched: function (response) {
            response.json()
                .then(main.onConversionToJsonSuccessful)
        },
        createGameList: function () {
            for (i = 0; i < this.gameData.games.length; i++) {
                this.game = {
                    date: "",
                    game: 0,
                    player1: "",
                    player2: "",
                    gameLink: 0
                }
                this.date = new Date(this.gameData.games[i].created);
                this.game.date = this.date.toLocaleString()
                this.game.game = this.gameData.games[i].id
                this.game.player1 = this.gameData.games[i].gameplayers[0].player.username
                if (this.gameData.games[i].gameplayers.length > 1) {
                    this.game.player2 = this.gameData.games[i].gameplayers[1].player.username;
                    if (this.gameData.player.id == this.gameData.games[i].gameplayers[1].player.id) {
                        this.game.gameLink = "/web/game.html?gp=" + this.gameData.games[i].gameplayers[1].id
                    }
                } else this.gameplayer = "";
                if (this.gameData.player.id == this.gameData.games[i].gameplayers[0].player.id) {
                    this.game.gameLink = "/web/game.html?gp=" + this.gameData.games[i].gameplayers[0].id
                }
                this.gamesList.push(this.game)
            }
            var date = Date()
        },
        login: function () {
            let username = document.getElementById("username").value
            let password = document.getElementById("password").value
            fetch("/api/login", {
                    credentials: 'include',
                    method: 'POST',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: 'name=' + username + '&pwd=' + password,
                })
                .then(r => {
                    if (r.status == 200) {
                        this.createPlayerMessage = "Succesfull Login";
                        this.getDataObject()
                    } else {
                        this.createPlayerMessage = "Error"
                    }
                })
                .catch(function (res) {
                    console.log(res)
                })
        },
        logout: function () {
            fetch("/api/logout")
                .then(this.getDataObject())
                .catch();
        },
        createPlayer: function () {
            let username = document.getElementById("username").value
            let password = document.getElementById("password").value
            fetch("/api/players", {
                    credentials: 'include',
                    method: 'GET',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: 'name=' + username + '&pwd=' + password,
                })
                .then(r => {
                    if (r.status == 201) {
                        this.createPlayerMessage = "player created";
                        this.getDataObject()
                    } else if (r.status == 409) {
                        this.createPlayerMessage = "Name already in use"
                    } else if (r.status == 403) {
                        this.createPlayerMessage = "Please give a name"
                    }
                }).then(this.login())
                .catch(function (res) {
                    console.log(res)
                })
        },
        createGame: function () {
            fetch("/api/games", {
                    credentials: 'include',
                    method: 'POST'
                })
                .then(res => {
                    if (res.status == 201) {
                        res => res.json();
                        window.location.href = "/web/game.html?gp=" + res.gpid
                    } else {
                        alert("no player logged in")
                    }
                })
                .catch(function (res) {
                    console.log(res)
                })
        },
        joinGame: function(game){
             fetch("/api/games/" + game + "/players", {
                    credentials: 'include',
                    method: 'POST'
                })
                .then(res => res.json())
                .then(res => console.log(res))
                .catch(function (res) {
                    console.log(res)
                })
            }
         },
    created: function () {
        this.getDataObject()
        this.getScores()
    }
})

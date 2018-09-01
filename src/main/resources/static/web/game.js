var main = new Vue({
    el: '#main',
    data: {
        rows: ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
        columns: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
        gameData: {},
        shipLocations: [],
        usernames: [],
        locations: [],
        allowed: true,
        illegalShip: false,
        allShips: {
            containership: {
                shipLength: 5,
                orientation: "horizontal",
                locations: []
            },
            cruiseship: {
                shipLength: 4,
                orientation: "horizontal",
                locations: []
            },
            yacht: {
                shipLength: 3,
                orientation: "horizontal",
                locations: []
            },
            fishingboat: {
                shipLength: 3,
                orientation: "horizontal",
                locations: [],
            },
            speedboat: {
                shipLength: 2,
                orientation: "horizontal",
                locations: []
            }
        },

    },
    methods: {
        getDataObject: function (gamePlayer) {
            var fetchConfig =
                fetch("/api/game_view/" + gamePlayer, {
                    method: "GET",
                    credential: "include"
                }).then(
                    r => {
                        if (r.status == 401) {
                            this.peekingPlayer()
                        } else if (r.status == 200) {
                            this.onDataFetched(r)
                        }
                    },
                )
        },
        onConversionToJsonSuccessful: function (json) {
            main.gameData = json;
            console.log(this.gameData.gameview)
            if (this.gameData.gameview.ships != null) {
                this.getShips()
            }
            this.getPlayers()
            if (this.gameData.gameview.usersalvoes != null) {
                this.getSalvoes(this.gameData.gameview.usersalvoes, "salvo ", "green")
            }
            if (this.gameData.gameview.enemysalvoes != null) {
                this.getSalvoes(this.gameData.gameview.enemysalvoes, "ship ", "orange")
            }
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
            for (i = 0; i < this.gameData.gameview.ships.length; i++) {
                for (j = 0; j < this.gameData.gameview.ships[i].location.length; j++) {
                    this.shipLocations.push(this.gameData.gameview.ships[i].location[j])
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
                    if (shot != "" && salvotype == this.gameData.gameview.usersalvoes) {
                        cell.style.backgroundColor = color;
                        cell.innerHTML = salvotype[i].turn;
                    } else if (shot != "") {
                        cell.style.backgroundColor = color;
                        cell.innerHTML = salvotype[i].turn;
                    };
                    if (this.shipLocations.includes(shot) && salvotype == this.gameData.gameview.enemysalvoes) {
                        cell.style.backgroundColor = "red";
                    }
                }
            }
        },
        getPlayers: function () {
            let players = this.gameData.gameview.game.gameplayers
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
        },
        peekingPlayer: function () {
            document.getElementById("main").innerHTML = ""
            document.getElementById("main").innerHTML = "<h1>No Peeking!!</h1>"
        },
        determineLocation: function (location, ship) {
            this.allowed = true
            let shipName = eval("this.allShips." + ship)
            let shipLength = shipName.shipLength
            shipName.locations = []
            if (shipName.orientation == "horizontal") {
                for (i = location[1]; i < (parseInt(location[1]) + shipLength); i++) {
                    if (this.checkLocation(location[0] + i) == true) {
                        this.allowed = false;
                        console.log(this.checkLocation(location[0] + i))
                        break
                    } else {

                        shipName.locations.push(location[0] + i)
                    }
                    console.log(location[0] + i)
                }
            }
            if (shipName.orientation == "vertical") {
                let rowNumber = 0
                for (i = 0; i < this.rows.length; i++) {
                    if (location[0] == this.rows[i]) {
                        rowNumber = i-1
                    }
                }
                for (i = 0; i < shipLength; i++) {
                    if (this.checkLocation(this.rows[rowNumber] + location[1]) == true) {
                        this.allowed = false;
                        console.log(this.checkLocation(this.rows[rowNumber] + location[1]))
                        break
                    } else {

                        shipName.locations.push(this.rows[rowNumber] + location[1]);
                        rowNumber++
                        
                    }
                    console.log(this.rows[rowNumber] + location[1])
                }
            }
            
        },
        checkLocation: function (loc) {
            for (ship in this.allShips) {
//                console.log(ship)
                checkShip = eval("this.allShips." + ship + ".locations")
                
                if (checkShip.includes(loc)) {
                    this.allowed = false;
                console.log(checkShip)}
 
            }
        },
        Rotate: function (shipId) {
            var element = document.getElementById(shipId);
            var ship = eval("this.allShips." + shipId)
            var parent = document.getElementById(shipId).parentNode.id
            if (element.className === "normal") {
                ship.orientation = "vertical";
                this.determineLocation(parent, shipId)
                if (this.allowed == true) {
                    element.className = "rotate"
                }
            } else if (element.className === "rotate") {
                ship.orientation = "horizontal"
                this.determineLocation(parent, shipId)
                if (this.allowed == true)
                    element.className = 'normal'
            };



        }
    },
    //
    //    created: function () {
    //        this.getDataObject(this.paramObj());
    //    }
})

function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev, el) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    main.determineLocation(ev.target.id, data);
    if (el.firstChild || main.allowed == false) {


    } else {

        el.appendChild(document.getElementById(data));

    }

}

var main = new Vue({
    el: '#main',
    data: {
        rows: ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
        columns: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
        gameData: {},
        placing: true,
        shipLocations: [],
        usernames: [],
        salvo: [],
        locations: [],
        allowed: true,
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
            console.log(this.gameData)
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
                document.getElementById("ship " + this.shipLocations[i]).style.backgroundImage = "none"
            }
        },
        getSalvoes: function (salvotype, grid, color) {
            for (i = 0; i < salvotype.length; i++) {
                for (j = 0; j < salvotype[i].location.length; j++) {
                    let shot = salvotype[i].location[j];
                    let cell = document.getElementById(grid + shot);
                    if (shot != "" && salvotype == this.gameData.gameview.usersalvoes) {
                        cell.style.backgroundColor = color;
                        cell.style.backgroundImage = "none";
                        cell.innerHTML = salvotype[i].turn;
                    } else if (shot != "") {
                        cell.style.backgroundColor = color;
                        cell.style.backgroundImage = "none";
                        cell.innerHTML = salvotype[i].turn;
                    };
                    if (this.shipLocations.includes(shot) && salvotype == this.gameData.gameview.enemysalvoes) {
                        cell.style.backgroundColor = "red";
                        cell.style.backgroundImage = "none";
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
            let locations = []
            if (shipName.orientation === "horizontal") {
                for (i = location.slice(1); i < (parseInt(location.slice(1)) + shipLength); i++) {
                    if (i < 11) {

                        this.checkLocation((location[0] + i), ship)
                    } else {
                        console.log("false");
                        this.allowed = false
                    }
                    if (this.allowed == true) {
                        locations.push(location[0] + i)
                    }
                }
            }
            if (shipName.orientation === "vertical") {
                let rowNumber = 0
                for (i = 0; i < this.rows.length; i++) {
                    if (location[0] == this.rows[i]) {
                        rowNumber = i
                    }

                }
                for (i = 0; i < shipLength; i++) {
                    if (rowNumber < 10) {
                        this.checkLocation((this.rows[rowNumber] + location.slice(1)), ship)
                    } else {
                        console.log("false");
                        this.allowed = false
                    }
                    if (this.allowed == true) {
                        locations.push(this.rows[rowNumber] + location.slice(1));
                        rowNumber++
                    }
                }
            }
            if (this.allowed == true) {
                shipName.locations = locations
            }
        },
        checkLocation: function (loc, ship) {
            this.allowed = true
            for (vessel in this.allShips) {
                checkShip = eval("this.allShips." + vessel + ".locations")
                if (vessel !== ship) {
                    if (checkShip.includes(loc)) {
                        this.allowed = false;
                    }
                }
            }
        },
        Rotate: function (shipId) {
            var element = document.getElementById(shipId);
            var ship = eval("this.allShips." + shipId)
            var parent = document.getElementById(shipId).parentNode.id
            var container = document.getElementById(shipId).parentNode.className
            if (container != "shipcontainer") {
                if (element.className === "normal") {
                    ship.orientation = "vertical";
                    this.determineLocation(parent, shipId)
                    if (this.allowed == true) {
                        element.className = "rotate"
                    } else {
                        ship.orientation = "horizontal"
                    }
                } else if (element.className === "rotate") {
                    ship.orientation = "horizontal"
                    this.determineLocation(parent, shipId)
                    if (this.allowed == true) {
                        element.className = "normal"
                    } else {
                        ship.orientation = "vertical"
                    }
                }
            }
        },
        makeShipJSON: function () {
            shipList = []
            for (vessel in this.allShips) {

                locations = eval("this.allShips." + vessel + ".locations")
                if (locations.length > 0) {
                    ship = {}
                    ship.shipType = vessel
                    ship.shipLocations = locations
                    shipList.push(ship)
                } else {
                    alert("not all ships have been placed!!");
                    break
                }
            }
            if (shipList.length == 5) {
                this.sendShips(shipList, this.paramObj())
            }

            console.log(shipList)

        },
        sendShips: function (ships, gamePlayer) {
            fetch("/api/games/players/" + gamePlayer + "/ships", {
                    credentials: 'include',
                    method: 'POST',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(ships)
                }).then(response => console.log(response))
                .then(this.placing = false)
                .then(setTimeout(3000, this.getDataObject(this.paramObj())))
        },
        sendSalvo: function (gamePlayer) {
            if (this.salvo.length == 3) {
                salvo = {
                    turn: 2,
                    salvoLocations: this.salvo
                }
                console.log(salvo)
                fetch("/api/games/players/" + gamePlayer + "/salvos", {
                        credentials: 'include',
                        method: 'POST',
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(salvo)
                    }).then(response => console.log(response))
                    .then(this.salvo = [])
            } else {
                alert("please fire 3 shots")
            }

        },
        placeShot: function (shotCell) {
            if (!this.salvo.includes(shotCell)) {
                if (this.salvo.length < 3) {
                    console.log(this.salvo.includes(shotCell))
                    document.getElementById("salvo " + shotCell).style.backgroundColor = "red"
                    document.getElementById("salvo " + shotCell).style.backgroundImage = "none"
                    this.salvo.push(shotCell);

                } else {
                    console.log(this.salvo);
                    alert("3 shots fired!")
                }
            } else {
                this.salvo = this.salvo.filter(e => e !== shotCell);
                document.getElementById("salvo " + shotCell).style.backgroundColor = ""

            }
        }


    },
})

function allowDrop(ev) {
    ev.preventDefault();

}

 function drag (event) {
                var img = document.createElement("img");
                    img.src= "speedboat.png" 
                        event.dataTransfer.setDragImage (img, 50, 50);
             event.dataTransfer.setData("text", event.target.id);
  
}

function drop(event, el) {
    event.preventDefault();
    var data = event.dataTransfer.getData("text");
    console.log(data)
    main.determineLocation(event.target.id, data);
    if (el.firstChild || main.allowed == false) {
        console.log("magnie")
    } else {
        el.appendChild(document.getElementById(data));
        event.target.style.backgroundColor = ""
    }
}

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
        allShots: [],
        locations: [],
        allowed: true,
        currentShip: [],
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
                locations: []
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
            if (this.gameData.gameview.ships.length == 5) {
                this.getShips()
                this.getPlayers()
                this.placing = false
                if (this.gameData.gameview.usersalvoes != null) {
                    this.getSalvoes(this.gameData.gameview.usersalvoes, "salvo ")
                }
                if (this.gameData.gameview.enemysalvoes != null) {
                    this.getSalvoes(this.gameData.gameview.enemysalvoes, "ship ")
                };

            } else if (
                this.gameData.gameview.ships.length < 5) {
                this.getShips()
                this.placing = true;
            } else {
                this.placing = true;
            }

        },
        onDataFetched: function (response) {
            response.json()
                .then(this.onConversionToJsonSuccessful)
        },
        determineGamePlayer: function () {
            var url = location.search;
            var x = url.split('=')[1];
            this.gamePlayer = x;
            return this.gamePlayer
        },
        getShips: function () {
            for (i = 0; i < this.gameData.gameview.ships.length; i++) {
                let ship = this.gameData.gameview.ships
                currentShip = eval("this.allShips." + ship[i].shipType)
                if (currentShip.locations.length == 0) {
                    for (j = 0; j < ship[i].shipLocations.length; j++) {
                        currentShip.locations.push(ship[i].shipLocations[j])
                    }
                }
                let cell = document.getElementById("ship " + currentShip.locations[0])
                if (cell.hasChildNodes() == false) {
                    let elem = document.getElementById(ship[i].shipType);
                    elem.style.display = "block"
                    if (currentShip.locations[0][0] == currentShip.locations[1][0]) {
                        elem.className = "normal";
                        currentShip.orientation = "horizontal"
                    } else {
                        elem.className = "rotate";
                        currentShip.orientation = "vertical"
                    }
                    cell.appendChild(elem);
                }
            }
        },
        getSalvoes: function (salvotype, grid) {

            for (i = 0; i < salvotype.length; i++) {
                for (j = 0; j < salvotype[i].location.length; j++) {
                    let shot = salvotype[i].location[j];
                    let cell = document.getElementById(grid + shot);
                    if (shot != "" && salvotype == this.gameData.gameview.usersalvoes) {
                        if (!this.gameData.gameview.userhits.includes(shot)) {
                            cell.className += " miss";
                        } else {
                            //                            cell.className += " hit";
                            var img = document.createElement("img");
                            img.src = "explosion.png";
                            if (cell.hasChildNodes()) {
                                if (cell.firstChild.className == "rotate" || cell.firstChild.className == "normal") {
                                    cell.appendChild(img);
                                }
                            } else {cell.appendChild(img)};
                        }
                        this.allShots.push(shot)
                    } else if (shot != "") {
                        cell.className += " miss";
                    };
                    for (var vessel in this.allShips) {
                        locations = eval("this.allShips." + vessel + ".locations")
                        if (locations.includes(shot) && salvotype == this.gameData.gameview.enemysalvoes) {
                            cell.className = "shipTable";
                            //                            cell.className += " hit";
                            var img = document.createElement("img");
                            img.src = "explosion.png";
                            if (cell.hasChildNodes()) {
                                if (cell.firstChild.className != "rotate" && cell.firstChild.className != "normal") {
                                    cell.appendChild(img);
                                }
                            } else {cell.appendChild(img)};
                        }
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
                for (i = location.slice(6); i < (parseInt(location.slice(6)) + shipLength); i++) {
                    if (i < 11) {

                        this.checkLocation((location[5] + i), ship)

                    } else {

                        this.allowed = false
                    }
                    if (this.allowed == true) {
                        locations.push(location[5] + i)
                    }
                }
            }
            if (shipName.orientation === "vertical") {
                let rowNumber = 0
                for (i = 0; i < this.rows.length; i++) {
                    if (location[5] == this.rows[i]) {
                        rowNumber = i
                    }

                }
                for (i = 0; i < shipLength; i++) {
                    if (rowNumber < 10) {
                        this.checkLocation((this.rows[rowNumber] + location.slice(6)), ship)
                    } else {

                        this.allowed = false
                    }
                    if (this.allowed == true) {
                        locations.push(this.rows[rowNumber] + location.slice(6));
                        rowNumber++
                    }
                }
            }
            if (this.allowed == true) {
                shipName.locations = locations
            }
        },
        checkLocation: function (loc, ship) {
            for (vessel in this.allShips) {
                checkShip = eval("this.allShips." + vessel + ".locations")
                if (vessel !== ship) {
                    if (checkShip.includes(loc)) {
                        this.allowed = false;

                    }
                }
            }
        },
        rotateShip: function (shipId) {
            if (this.placing) {
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
            this.sendShips(shipList, this.determineGamePlayer())
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
                })
                .then(response => console.log(response))
                .then(r => {
                    this.placing = false
                    this.getDataObject(this.determineGamePlayer())
                })
        },
        sendSalvo: function () {
            if (this.salvo.length == 3) {
                salvo = {
                    turn: 2,
                    salvoLocations: this.salvo
                }

                fetch("/api/games/players/" + this.determineGamePlayer() + "/salvos", {
                        credentials: 'include',
                        method: 'POST',
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(salvo)
                    }).then(response => console.log(response))
                    .then(this.salvo = [])
                    .then(r => {
                        this.getDataObject(this.determineGamePlayer())
                    })
            } else {
                alert("please fire 3 shots")
            }
        },
        placeShot: function (shotCell) {
            console.log(this.allShots)
            if (!this.allShots.includes(shotCell) || this.salvo.includes(shotCell)) {
                if (!this.salvo.includes(shotCell)) {
                    if (this.salvo.length < 3) {
                        document.getElementById("salvo " + shotCell).className += " shot"
                        this.salvo.push(shotCell);
                        this.allShots.push(shotCell)
                        console.log(this.salvo)
                    } else {
                        alert("3 shots fired!")
                    }
                } else {
                    this.salvo = this.salvo.filter(e => e !== shotCell);
                    this.allShots = this.allShots.filter(e => e !== shotCell);
                    document.getElementById("salvo " + shotCell).className = "salvoTable"
                    console.log("delete shot")
                }
            }
        }
    },
    created: function () {
        this.getDataObject(this.determineGamePlayer())
    }

})


function allowDrop(ev) {
    ev.preventDefault();

}

function drag(event) {
    if (main.placing) {
        event.dataTransfer.setData("text", event.target.id);
    }

}

function drop(event, el) {
    if (main.placing) {
        event.preventDefault();
        var data = event.dataTransfer.getData("text");
        main.determineLocation(event.target.id, data);
        if (el.firstChild || main.allowed == false) {} else {
            el.appendChild(document.getElementById(data));

        }
    }
}

var main = new Vue({
    el: '#main',
    data: {
        rows: ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
        columns: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
        gameDate: {},
        shipLocations: [],
        usernames: []

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
            this.getShipLocations()
            this.getPlayers()
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
        getShipLocations: function (){
            for (i=0; i< this.gameData.ships.length; i++ ){
                for(j=0; j<this.gameData.ships[i].location.length; j++){
                    this.shipLocations.push(this.gameData.ships[i].location[j])
                }}
            for(i=0; i<this.shipLocations.length; i++){
                document.getElementById(this.shipLocations[i]).style.backgroundColor = "red"
                            }
                console.log(this.shipLocations)
            
          
            
        },
        getPlayers: function(){0
            for (i=0; i<2; i++){if (this.gameData.game.gameplayers[i].id == this.gamePlayer.gp)
            {this.usernames.push(this.gameData.game.gameplayers[i].player.username  + " (You)")}else {this.usernames.push(this.gameData.game.gameplayers[i].player.username)}
            
        }}
    },
    created: function () {
        this.gamePlayer = this.paramObj(location.search)
        this.getDataObject(this.gamePlayer.gp)
        
            }

})



console.log("test")

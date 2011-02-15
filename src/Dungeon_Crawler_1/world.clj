(ns Dungeon-Crawler-1.world
  (:use [Dungeon-Crawler-1.state] :reload)
  (:use [Dungeon-Crawler-1.core] :reload)
)
(def lobby (struct location "lobby" "lobbyDescription" #{} #{} #{}))
(def startLocation (ref lobby))
(def worldReference (ref (struct world #{} {})))
(def testMap
     { 
      :person1 (struct player "person1" :location1 #{})
      :person2 (struct player "person2" :location2 #{})
      :person3 (struct player "person3" :location1 #{})
      })

(defn setWorld [worldIn] (dosync (ref-set worldReference worldIn)))
(defn setStartLocation [locationIn] (dosync (ref-set startLocation locationIn)))
(defn getWorldLocations [] (worldReference :locations))

(defn getWorldPlayers [] (worldReference :players))

(defn addWorldLocation [locationKeyIn locationIn] (dosync (ref-set worldReference (update-in (deref worldReference) [:locations] assoc locationKeyIn locationIn))))

(defn getPlayersInLocation [locationIn]
  ;(:name (deref (:locationKey (deref (first (worldReference :players))))))
  (filter #(= ((deref ((deref %) :locationKey)) :name) locationIn) (worldReference :players))
  ;(filter #(not= ((deref ((deref %) :locationKey)) :name) locationIn) (worldReference :players))
  )

(defn addWorldPlayer [person]
  (dosync
   (do
     (ref-set worldReference (update-in (deref worldReference) [:players] #(conj % person)))
     (ref-set person (stateAddLocationToPlayer person (deref startLocation)))
     )))

(defn moveWorldPlayer [person locationIn]
  (dosync
   (ref-set person (stateAddLocationToPlayer person locationIn))
   )
  )

(defn getPlayerFromName [playerName]
  (first (filter #(= (% :name) playerName) (worldReference :players)))
  )

(defn getLocationFromName [locationName]
  ;(vals (worldReference :locations))
  (first (filter #(= (% :name) locationName) (vals (worldReference :locations))))
  )

(defn playerInGame? [playerName]
  (not= () (filter #(= (% :name) playerName) (worldReference :players)))
  )

(defn locationExists? [locationName]
  (not= nil (getLocationFromName locationName))
  )
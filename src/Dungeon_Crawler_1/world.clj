(ns Dungeon-Crawler-1.world
  (:use [Dungeon-Crawler-1.state] :reload)
  (:use [Dungeon-Crawler-1.core] :reload)
)
(def lobby (struct location "lobby" "lobbyDescription" #{} #{} #{}))
(def startLocation (ref lobby))
(def worldReference (ref (struct world #{} #{})))

(defn setWorld [worldIn] (dosync (ref-set worldReference worldIn)))
(defn setStartLocation [locationIn] (dosync (ref-set startLocation locationIn)))
(defn getWorldLocations [] (worldReference :locations))

(defn getWorldPlayers [] (worldReference :players))

(defn addWorldLocation [locationIn] (dosync (ref-set worldReference (update-in (deref worldReference) [:locations] #(conj % locationIn)))))

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

(defn getLocationFromName [locationName]
  (first (filter #(= (% :name) locationName) (worldReference :locations)))
  )

(defn playerInGame? [playerName]
  (not= () (filter #(= (% :name) playerName) (worldReference :players)))
  )

(defn locationExists? [locationName]
  (not= nil (getLocationFromName locationName))
  )
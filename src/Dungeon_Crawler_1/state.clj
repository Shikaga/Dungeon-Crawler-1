(ns Dungeon-Crawler-1.state
  (:use [Dungeon-Crawler-1.core] :reload)
  )

;(def goldkey (struct item "key" "opens a lock somewhere"))
;(def rope (struct item "rope" "just enough to hang yourself"))
;(def player1Inventory (ref (struct inventory "You Inventory" '(goldkey))))
;(def player1 (struct player "Jon" player1Inventory))

;(def initWorld (ref (struct world {:player1 player1} {:outside location})))
;(def currentRoom (ref 'outside))

(defn stateAddPlayerToLocation [location player] (dosync (ref-set location (addPlayerToLocation (deref location) player))))
(defn stateAddLocationToPlayer [player location] (dosync (ref-set player (addLocationToPlayer (deref player) location))))
;(defn goNorth [] (dosync (ref-set currentRoom 'manor)))
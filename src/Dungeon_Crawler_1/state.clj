(ns Dungeon-Crawler-1.state)
(load-file "./core.clj")
(refer 'Dungeon-Crawler-1.core)

(def goldkey (struct item "key" "opens a lock somewhere"))
(def rope (struct item "rope" "just enough to hang yourself"))
(def player1Inventory (ref (struct inventory "You Inventory" '(goldkey))))
(def player1 (struct player "Jon" 'outside player1Inventory))

(def initWorld (ref (struct world {:player1 player1} {:outside location})))
(def currentRoom (ref 'outside))

(defn goNorth [] (dosync (ref-set currentRoom 'manor)))
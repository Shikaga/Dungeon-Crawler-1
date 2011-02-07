(ns Dungeon-Crawler-1.core)

(defstruct player :name :location :inventory)
(defstruct location :name :description :items)
(defstruct item :name :description)
(defstruct inventory :name :items)
(defstruct world :players :locations)

(defn listItems [inventory] (print (inventory :items)))
(defn addItem [inventory item] (dosync (ref-set inventory (update-in (deref inventory) [:items] #(conj % item)))))
(defn pickupItem [player item] (update-in player [:inventory] #(addItem % item)))
(defn getItems [room] (map :name (room :items))) ;Cool example
(defn itemInLocation? [location item] (contains? (location :items) item))
(defn removeItemFromLocation "returns removed item if present else empty list" [location item]
  (if (itemInLocation? location item) (do (dosync (ref-set location (update-in (deref location) [:items] #(disj % item)))) true) false))
(defn moveItemFromLocationToInventory [location inventory item] (if (removeItemFromLocation location item) (addItem inventory item) nil))
(defn getItemFromName [items name] (first (filter #(not= (% :name) name) items)))
(def goldkey (struct item "key" "opens a lock somewhere"))
(def rope (struct item "rope" "just enough to hang yourself"))

(def player1Inventory (ref (struct inventory "You Inventory" '(goldkey))))
(def myInventory2 (struct inventory "yi" '(goldkey)))
(def outside (struct location "outside" "You are outside!" #{'rope}))
(def manor (struct location "Manor" "You stand in a manor!" #{}))
(def player1 (struct player "Jon" outside player1Inventory))
(def initWorld (ref (struct world {:player1 player1} {:outside location})))
(def currentRoom (ref outside))

(defn goNorth [] (dosync (ref-set currentRoom manor)))

(def flag true)
(println "> ")
(while flag
  (def line (read-line))
  (if (= line "lol") (println "no you!") ())
  (if (= line "help") (println "RTFM noob!") ())
  (if (= line "look") (println (currentRoom :description)))
  (if (= line "north") (goNorth))
)

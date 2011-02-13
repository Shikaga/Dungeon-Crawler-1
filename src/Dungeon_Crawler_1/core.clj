(ns Dungeon-Crawler-1.core)

(defstruct player :name :location :inventory)
(defstruct location :name :description :exits :items :players)
(defstruct item :name :description)
(defstruct inventory :name :items)
(defstruct world :players :locations)
(defstruct exit :direction :location)

(defn listItems [inventory] (print (inventory :items)))
(defn addItem [inventory item] (update-in inventory [:items] #(conj % item)))
(defn pickupItem [player item] (update-in player [:inventory] #(addItem % item)))
(defn getItems [room] (map :name (room :items))) ;Cool example
(defn itemInLocation? [location item] (contains? (location :items) item))
(defn removeItemFromLocation "returns removed item if present else empty list" [location item]
  (if (itemInLocation? location item) (do (update-in location [:items] #(disj % item)) true) false))
(defn moveItemFromLocationToInventory [location inventory item] (if (removeItemFromLocation location item) (addItem inventory item) nil))
(defn getItemFromName [items name] (first (filter #(not= (% :name) name) items)))
(defn addPlayerToLocation [location player] (update-in location [:players] #(conj % player)))
(defn addLocationToPlayer [player location] (assoc-in player [:location] location))
(def directions #{:north :east :west :south})

(def locationMap {'manor (struct location "Manor" "You stand in a manor!" #{(struct exit :south 'outside)} #{})
'outside (struct location "outside" "You are outside!" #{(struct exit :north 'manor)} #{'rope})})





(ns Dungeon-Crawler-1.core)
(defstruct player :name :location)
(defstruct location :name :description)
(defstruct item :name :description)
(defstruct inventory :name :items)

(defn incr [x] (+ x 1))

(defn listItems [inventory] (print (inventory :items)))

(defn addItems [inventory item] (update-in inventory [:items] #(conj % item)))
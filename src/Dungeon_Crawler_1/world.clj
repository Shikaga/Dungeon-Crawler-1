(ns Dungeon-Crawler-1.world
  (:use [Dungeon-Crawler-1.core] :reload)
)

(def worldReference (ref (struct world #{} #{})))

(defn setWorld [worldIn] (dosync (ref-set worldReference worldIn)))

(defn getWorldLocations [] (worldReference :locations))

(defn getWorldPlayers [] (worldReference :players))

(defn addWorldLocations [location] (dosync (ref-set worldReference (update-in (deref worldReference) [:locations] #(conj % location)))))

(defn addWorldPerson [person] (dosync (ref-set worldReference (update-in (deref worldReference) [:players] #(conj % person)))))
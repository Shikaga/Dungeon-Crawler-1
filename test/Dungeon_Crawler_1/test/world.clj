(ns Dungeon-Crawler-1.test.world
  (:use [Dungeon-Crawler-1.world] :reload)
  (:use [Dungeon-Crawler-1.core] :reload)  
  (:use [clojure.test]))

(def testLocation (ref (struct location "location1" "location1 Description" #{} #{} #{})))
(def testPerson (ref (struct player "name" #{})))

(deftest basicTest
  (is (= 1 1)))

(deftest testGetWorldLocations
  (do
    (setWorld (struct world #{} #{}))
    (is (= #{} (getWorldLocations))))
  )

(deftest testAddLocation
  (do
    (setWorld (struct world #{} #{}))
    (addWorldLocations testLocation)
    (is (= #{testLocation} (getWorldLocations)))
    )
  )

(deftest testAddPerson
  (do
    (setWorld (struct world #{} #{}))
    (addWorldPerson testPerson)
    (is (= #{testPerson} (getWorldPlayers)))
    )
  )
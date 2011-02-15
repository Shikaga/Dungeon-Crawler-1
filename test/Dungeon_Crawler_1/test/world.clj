(ns Dungeon-Crawler-1.test.world
  (:use [Dungeon-Crawler-1.world] :reload)
  (:use [Dungeon-Crawler-1.core] :reload)  
  (:use [clojure.test]))

(def testLocation (ref (struct location "location1" "location1 Description" #{} #{} #{})))
(def testLocation2 (ref (struct location "location2" "location2 Description" #{} #{} #{})))
(def testPerson (ref (struct player "name" nil #{})))
(def testPerson2 (ref (struct player "name2" nil #{})))

(defn reset []
  (dosync
    (setWorld (struct world #{} {}))
    (ref-set testLocation (struct location "location1" "location1 Description" #{} #{} #{}))
    (ref-set testLocation2 (struct location "location2" "location2 Description" #{} #{} #{}))
    (ref-set testPerson (struct player "name" nil #{}))
    )
  )

(deftest basicTest
  (is (= 1 1)))

(deftest testGetWorldLocations
  (do
    (setWorld (struct world #{} {}))
    (is (= {} (getWorldLocations))))
  )

(deftest testAddLocation
  (do
    (reset)
    (addWorldLocation :testLocation testLocation)
    (is (= {:testLocation testLocation} (getWorldLocations)))
    )
  )

(deftest testAddPlayer
  (do
    (reset)
    (setStartLocation testLocation)
    (addWorldPlayer testPerson)
    (is (= #{testPerson} (getWorldPlayers)))
    (is (= testLocation ((first (getWorldPlayers)) :locationKey)))
    )
  )

(deftest testGetPlayerFromName
  (do
    (reset)
    (setStartLocation testLocation)
    (addWorldPlayer testPerson)
    (addWorldPlayer testPerson2)
    (is (= testPerson (getPlayerFromName "name")))
    (is (= testPerson2 (getPlayerFromName "name2")))
    ))

(deftest testGetPlayersInLocation
  (do
    (reset)
    (setStartLocation testLocation)
    (addWorldPlayer testPerson)
    (is (= testLocation ((first (getWorldPlayers)) :locationKey)))
    (is (= testPerson (first (getPlayersInLocation "location1"))))
    (moveWorldPlayer testPerson testLocation2)
    (is (= nil (first (getPlayersInLocation "location1"))))
					;    (moveWorldPlayer testPerson testLocation2)
    ))

(deftest testMovePlayer
  (do
    (reset)
    (setStartLocation testLocation)
    (addWorldPlayer testPerson)
    (moveWorldPlayer testPerson testLocation2)
    (is (= testLocation2 (testPerson :locationKey)))
    ))

(deftest testGetLocationFromName
  (do
    (reset)
    (addWorldLocation :testLocation testLocation)
    (addWorldLocation :testLocation2 testLocation2)
    (is (= testLocation (getLocationFromName "location1")))
    (is (= testLocation2 (getLocationFromName "location2")))
    )
  )

(deftest testPlayerExists
  (do
    (reset)
    (setStartLocation testLocation)
    (addWorldPlayer testPerson)
    (is (= (playerInGame? "name") true))
    (is (= (playerInGame? "name2") false))
    )
  )

(deftest testLocationExists
  (do
    ;(reset)
    (addWorldLocation :location1 location)
    ;(is (= (locationExists? "location1") true))
    ;(is (= (locationExists? "location2") false))
    )
  )

(deftest testTestMap
  (is (= "person1" ((testMap :person1) :name)))
  (is (= "person2" ((testMap :person2) :name)))
  )
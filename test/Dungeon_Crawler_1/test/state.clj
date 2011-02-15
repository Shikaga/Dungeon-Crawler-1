(ns Dungeon-Crawler-1.test.state
  (:use [Dungeon-Crawler-1.state] :reload)
  (:use [Dungeon-Crawler-1.core] :reload)  
  (:use [clojure.test]))

(def testLocation (ref (struct location "location1" "location1 Description" #{} #{} #{})))

(def testLocation2 (ref (struct location "location2" "location2 Description" #{} #{} #{})))

(def testPlayer (ref (struct player "player1" testLocation #{})))

(deftest basictest
  (is (= 1 1)))

(deftest testStateAddPlayerToLocation
  (is (= #{testPlayer} ((stateAddPlayerToLocation testLocation testPlayer) :playerKeys)))
  )

(deftest testStateAddLocationToPlayer
  (is (= testLocation2 ((stateAddLocationToPlayer (ref (struct player "player1" testLocation #{})) testLocation2) :locationKey))))

(deftest testAddExitToLocation
  (do
    (stateAddExitToLocation testLocation2 testLocation) 
  ))
(ns Dungeon-Crawler-1.test.state
  (:use [Dungeon-Crawler-1.state] :reload)
  (:use [Dungeon-Crawler-1.core] :reload)  
  (:use [clojure.test]))

(def testPlayer (ref (struct player "player1" #{})))

(def testLocation (ref (struct location "location1" "location1 Description" #{} #{} #{})))

(deftest basictest
  (is (= 1 1)))

(deftest testStateAddPlayerToLocation
  (is (= #{testPlayer} ((stateAddPlayerToLocation testLocation testPlayer) :players)))
  )


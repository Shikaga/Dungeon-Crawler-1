(ns Dungeon-Crawler-1.test.core
  (:use [Dungeon-Crawler-1.core] :reload)
  (:use [clojure.test]))

(def testItem1 (struct item "item1" "item of power!"))
(def testItem2 (struct item "item2" "item of current!"))
(def testItem3 (struct item "item3" "item of resistance!"))

(def testLocation (struct location "testName" "testDescription" #{} #{testItem1 testItem2} #{}))
(def testInventory (struct inventory "testInventory" #{testItem1 testItem2}))
(def testPlayer (struct player "Jon" testInventory))

(deftest addItemsTest
  (is (= #{testItem1 testItem2 testItem3} ((addItem testInventory testItem3) :items))))

(deftest pickupItemTest
  (is (= #{testItem1 testItem2 testItem3} (((pickupItem testPlayer testItem3) :inventory) :items))))

(deftest itemInLocation?Test
  (is (= true (itemInLocation? testLocation testItem1))))

(deftest removeItemLocationTest
  (def removedInventory (removeItemFromLocation testLocation testItem1)))

(deftest addPlayerToLocationTest
  (is (= #{} (testLocation :players)))
  (is (= #{testPlayer} ((addPlayerToLocation testLocation testPlayer) :players)))
  )
(ns Dungeon-Crawler-1.test.core
  (:use [Dungeon-Crawler-1.core] :reload)
  (:use [Dungeon-Crawler-1.world] :reload)
  (:use [clojure.test]))

(def testLocation (struct location "testName" "testDescription"))

(def testItem1 (struct item "item1" "item of power!"))
(def testItem2 (struct item "item2" "item of current!"))
(def testItem3 (struct item "item3" "item of resistance!"))

(def testInventory (struct inventory "testInventory" '(testItem1 testItem2)))

(def testPlayer (struct player "Jon" testLocation testInventory))

(deftest incr-works
  (is (= 2 (incr 1))))

(deftest jon-name-is-Jon
  (is (= "Jon" (:name testPlayer))))

(deftest jon-location-is-greatHall
  (is (= testLocation (:location testPlayer))))

(deftest mainHallDescription
  (is (= "testDescription" (:description testLocation))))

(deftest addItemsTest
  (is (= '(testItem3 testItem1 testItem2) ((addItems testInventory 'testItem3) :items))))

(deftest pickupItemTest
  (is (= '(testItem3 testItem1 testItem2) (((pickupItem testPlayer 'testItem3) :inventory) :items))))
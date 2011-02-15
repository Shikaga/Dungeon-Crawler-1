;http://nakkaya.com/2010/02/10/a-simple-clojure-irc-client/
(ns Dungeon-Crawler-1.irc
  (:use [Dungeon-Crawler-1.world] :reload)
  (:use [Dungeon-Crawler-1.core] :reload)  
  (:import (java.net Socket)
            (java.io PrintWriter InputStreamReader BufferedReader)))

 (def freenode {:name "irc.freenode.net" :port 6667})
 (def user {:name "Jonathan Paul" :nick "shikagatesterthing..."})

 (declare conn-handler)

 (defn connect [server]
   (let [socket (Socket. (:name server) (:port server))
         in (BufferedReader. (InputStreamReader. (.getInputStream socket)))
         out (PrintWriter. (.getOutputStream socket))
         conn (ref {:in in :out out})]
     (doto (Thread. #(conn-handler conn)) (.start))
     conn))

 (defn write [conn msg]
   (doto (:out @conn)
     (.println (str msg "\r"))
     (.flush)))

(defn printName [sequence]
  (if (empty? sequence)
    ""
    (str (first sequence) ", " (printName (rest sequence)))
   )
  )

(defn myContains? [sequence comparison]
  (if (empty? sequence)
    false
    (if (= (first sequence) comparison) true (myContains? (rest sequence) comparison))
    ))
      
 (defn conn-handler [conn]
   (while 
    (nil? (:exit @conn))
    (let [msg (.readLine (:in @conn))]
      (println (str "|" msg "|"))
      (cond 
       (re-find #"^ERROR :Closing Link:" msg) 
       (dosync (alter conn merge {:exit true}))
       (re-find #"^PING" msg)
       (write conn (str "PONG "  (re-find #":.*" msg)))
       (re-find #".* PRIVMSG.*PING" msg)
       (do
	 (write conn (str "PRIVMSG #shikagatest :oh good for you!"))
	 (write conn (str "PRIVMSG shikaga :and good for you too!"))
	 )
       (re-find #".* PRIVMSG.*" msg)
       (let [[_ name _ _ message] (re-matches #":(.*)!(.*PRIVMSG )([A-Za-z0-9-]+) :(.*)" msg)]
	 (do
	   (if (not (playerInGame? name))
	   (do
	     (addWorldPlayer (ref (struct player name :word #{})))
	     (write conn (str "PRIVMSG " name " : Current Players: " (getWorldPlayers)))
	     )
	   (do
	     (cond 
	      (re-find #"go" message)
	      (let [[_ locationString] (re-matches #"go (.*)" message)]
		(if (locationExists? locationString)
		  (do 
		    (write conn (str "PRIVMSG " name " : Location Exists"))
		    (moveWorldPlayer (getPlayerFromName name) (getLocationFromName locationString))
		    (write conn (str "PRIVMSG " name " : Players in your room : " (printName (map :name (map deref (getPlayersInLocation locationString))))))
		    )
		  (write conn (str "PRIVMSG " name " : Cannot go there"))
		  )
		)
	      (re-find #"say" message)
	      (let [[_ user userMessage] (re-matches #"say ([A-Za-z0-9-]+) (.*)" message)]
		(do
		  (if (= nil (getPlayerFromName user))
		    (write conn (str "PRIVMSG " name " : nope"))
		    (if (myContains? (map :name (map deref (getPlayersInLocation (((deref (getPlayerFromName name)) :locationKey) :name)))) ((deref (getPlayerFromName user)) :name))
		      (write conn (str "PRIVMSG " user " : " name " says " userMessage))
		      )
		    )
		  )
		)
	      )
	     )
	   )))))))

 (defn login [conn user]
   (write conn (str "NICK " (:nick user)))
   (write conn (str "USER " (:nick user) " 0 * :" (:name user))))

(defn handleUserMessage [username message] ())

(def irc (connect freenode))
(login irc user)
(write irc "JOIN #shikagatest")

(def testLocation (ref (struct location "kitchen" "location1 Description" #{} #{} #{})))
(def testLocation2 (ref (struct location "office" "location2 Description" #{} #{} #{})))
(def testLocation3 (ref (struct location "batcave" "location2 Description" #{} #{} #{})))
(def testLocation4 (ref (struct location "board-room" "location2 Description" #{} #{} #{})))

(addWorldLocation :location1 testLocation)
(addWorldLocation :location2 testLocation2)
(setStartLocation testLocation)
;(write irc "QUIT")
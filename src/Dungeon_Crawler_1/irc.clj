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
	   (addWorldPlayer (struct player name #{}))
           (write conn (str "PRIVMSG " name " : Current Players: " (getWorldPlayers)))
	 )
       )))))
 (defn login [conn user]
   (write conn (str "NICK " (:nick user)))
   (write conn (str "USER " (:nick user) " 0 * :" (:name user))))

(defn handleUserMessage [username message] ())

(def irc (connect freenode))
(login irc user)
(write irc "JOIN #shikagatest")
;(write irc "QUIT")
(ns partsbin.monger.core.core
  (:require [partsbin.monger.core.alpha :as monger]
            [integrant.core :as ig]))

(derive ::connection ::monger/connection)
(derive ::uri-connection ::monger/uri-connection)
(derive ::database ::monger/database)

(def config
  {::connection          {:host "localhost"}
   [::config ::database] {:conn (ig/ref ::connection)
                          :db-name "config"}
   [::admin ::database]  {:conn (ig/ref ::connection)
                          :db-name "admin"}})

(comment
  (def sys (ig/init config))
  (ig/halt! sys))
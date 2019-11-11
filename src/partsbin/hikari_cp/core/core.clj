(ns partsbin.hikari-cp.core.core
  (:require [partsbin.hikari-cp.core.alpha :as cp]
            [integrant.core :as ig]))

;Note that this is linked to an alpha ns and could change.
(derive ::datasource ::cp/datasource)

(def config
  {::datasource {:adapter "h2"
                 :url     "jdbc:h2:mem:mem_only"}})

(comment
  (require '[next.jdbc.sql :as sql])
  (require '[clojure.java.jdbc :as j])

  (def sys (ig/init config))

  (let [{conn ::datasource} sys]
    (sql/query conn ["SELECT 1"]))

  (let [{conn ::datasource} sys]
    (j/query {:datasource conn} ["SELECT 1"]))

  (ig/halt! sys)

  ;Hikari is an 'unwrapped' connection pool and is natively compatible with next.jdbc.
  ;You must put it in a :datasource key for clojure.jdbc.
  (let [{:keys [::datasource] :as temp-sys} (ig/init config)]
    (println (j/query {:datasource datasource} "SELECT 1"))
    (ig/halt! temp-sys))
  )
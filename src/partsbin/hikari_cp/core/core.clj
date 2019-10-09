(ns partsbin.hikari-cp.core.core
  (:require [partsbin.hikari-cp.core.alpha :as cp]
            [integrant.core :as ig]))

;Note that this is linked to an alpha ns and could change.
(derive ::datasource ::cp/datasource)

(def config {::datasource {:adapter "h2"
                           :url     "jdbc:h2:mem:mem_only"}})

(comment
  (require '[clojure.java.jdbc :as j])
  (defonce sys (ig/init config))
  (ig/halt! sys)
  (let [{:keys [::datasource] :as temp-sys} (ig/init config)]
    (println (j/query {:datasource datasource} "SELECT 1"))
    (ig/halt! temp-sys))
  )
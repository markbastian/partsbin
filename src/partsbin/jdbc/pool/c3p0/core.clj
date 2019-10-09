(ns partsbin.jdbc.pool.c3p0.core
  (:require [partsbin.jdbc.pool.c3p0.alpha :as c3p0]
            [integrant.core :as ig]))


(derive ::datasource ::c3p0/datasource)

(def config {::datasource {:connection-uri "jdbc:h2:mem:mem_only"}})

(comment
  (require '[clojure.java.jdbc :as j])
  (defonce sys (ig/init config))
  (ig/halt! sys)
  (let [{:keys [::datasource] :as temp-sys} (ig/init config)]
    (println (j/query datasource "SELECT 1"))
    (ig/halt! temp-sys)))

(ns partsbin.core
  (:require [partsbin.system :refer [create start stop restart system]]
            [partsbin.datomic.api.core :as datomic]
            [partsbin.immutant.web :as web]
            [partsbin.clojure.java.jdbc.core :as jdbc]
            [clojure.java.jdbc :as j]
            [integrant.core :as ig]))

(defn app [{:keys [sql-conn] :as request}]
  (let [res (j/query sql-conn "SELECT 1")]
    {:status 200 :body (str "OK - " (into [] res))}))

(def config
  {::jdbc/connection    {:connection-uri "jdbc:h2:mem:mem_only"}
   ::datomic/database   {:db-uri  "datomic:mem://example"
                         :delete? true}
   ::datomic/connection {:database (ig/ref ::datomic/database)
                         :db-uri   "datomic:mem://example"}
   ::web/server         {:host         "0.0.0.0"
                         :port         3000
                         :sql-conn     (ig/ref ::jdbc/connection)
                         :datomic-conn (ig/ref ::datomic/connection)
                         :handler      #'app}})

(defonce sys (create config))

(comment
  (let [{:keys [::jdbc/connection]} (system sys)]
    (j/query connection "SELECT 1")))
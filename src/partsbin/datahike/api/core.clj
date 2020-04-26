(ns partsbin.datahike.api.core
  (:require [partsbin.datahike.api.alpha :as datahike]
            [integrant.core :as ig]
            [datahike.api :as d]))

(derive ::database ::datahike/database)
(derive ::connection ::datahike/connection)

(comment
  (require '[partsbin.system :refer [create-system]])

  (def schema
    [{:db/ident       :name
      :db/valueType   :db.type/string
      :db/cardinality :db.cardinality/one}])

  (def config {::database   {:db-file         "tmp/datahike"
                             :delete-on-halt? true
                             :initial-tx      schema}
               ::connection {:db-config (ig/ref ::database)}})

  (create-system config)

  (start)

  (let [{conn ::connection} (system)]
    (d/transact conn [{:name "Mark"}
                      {:name "Becky"}]))

  (let [{conn ::connection} (system)]
    (d/q
      '[:find [?n ...]
        :in $
        :where
        [_ :name ?n]]
      @conn))

  (stop))
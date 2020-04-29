(ns partsbin.datahike.api.core
  (:require [partsbin.datahike.api.alpha :as datahike]
            [integrant.core :as ig]
            [datahike.api :as d]))

(derive ::database ::datahike/database)
(derive ::connection ::datahike/connection)

(comment
  (require '[partsbin.system :refer [with-system]])

  (def schema
    [{:db/ident       :name
      :db/valueType   :db.type/string
      :db/cardinality :db.cardinality/one}])

  (def config {::database   {:db-file         "tmp/datahike"
                             :delete-on-halt? true
                             :initial-tx      schema}
               ::connection {:db-config (ig/ref ::database)}})

  (with-system
    [{conn ::connection} config]
    (d/transact conn [{:name "Mark"}
                      {:name "Becky"}])
    (d/q
      '[:find [?n ...]
        :in $
        :where
        [_ :name ?n]]
      @conn))
  )
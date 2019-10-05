(ns partsbin.datascript.core.core
  (:require [partsbin.datascript.core.alpha :as datascript]
            [integrant.core :as ig]
            [datascript.core :as d]))

(derive ::connection ::datascript/connection)

(def config {::connection {:name        {:db/unique :db.unique/identity}
                           :ingredients {:db/cardinality :db.cardinality/many}}})

(comment
  (defonce system (ig/init config))
  (let [{:keys [::connection]} system]
    (d/transact! connection [{:name        "BLT"
                              :ingredients [:bacon :lettuce :tomato]}
                             {:name        "taco"
                              :ingredients [:beef :lettuce :tomato]}]))
  (let [{:keys [::connection]} system]
    (d/q
      '[:find ?n ?nm ?i
        :in $
        :where
        [?e :name ?n]
        [?e :ingredients ?i]
        [?f :ingredients ?i]
        [(not= ?f ?e)]
        [?f :name ?nm]]
      @connection))
  (ig/halt! system))
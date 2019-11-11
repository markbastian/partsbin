(ns partsbin.crux.api.core
  (:require [partsbin.crux.api.alpha :as crux]
            [crux.api :as crux-api]
            [integrant.core :as ig]))

(derive ::node ::crux/node)

;https://juxt.pro/crux/docs/configuration.html
;https://juxt.pro/crux/docs/get_started.html#_get_started
(def config
  {::node {:crux.node/topology                 :crux.standalone/topology
           :crux.node/kv-store                 "crux.kv.memdb/kv"
           :crux.kv/db-dir                     "data/db-dir-1"
           :crux.standalone/event-log-dir      "data/eventlog-1"
           :crux.standalone/event-log-kv-store "crux.kv.memdb/kv"}})

(comment
  (def system (ig/init config))

  (let [{node ::node} system]
    (crux-api/submit-tx
      node
      [[:crux.tx/put
        {:crux.db/id :dbpedia.resource/Pablo-Picasso
         :name       "Pablo"
         :last-name  "Picasso"}
        #inst "2018-05-18T09:20:27.966-00:00"]]))

  (let [{node ::node} system
        db (crux-api/db node)]
    (crux-api/q
      db
      '{:find [e]
        :where [[e :name "Pablo"]]}))

  (let [{node ::node} system
        db (crux-api/db node)]
    (crux-api/entity
      db :dbpedia.resource/Pablo-Picasso))

  (ig/halt! system))
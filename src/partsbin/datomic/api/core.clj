(ns partsbin.datomic.api.core
  (:require [partsbin.datomic.api.alpha :as datomic]))

(derive ::database ::datomic/database)
(derive ::connection ::datomic/connection)

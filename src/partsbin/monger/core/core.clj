(ns partsbin.monger.core.core
  (:require [partsbin.monger.core.alpha :as monger]))

(derive ::connection ::monger/connection)
(derive ::uri-connection ::monger/uri-connection)


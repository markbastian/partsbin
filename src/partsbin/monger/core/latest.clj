(ns partsbin.monger.core.latest
  (:require [partsbin.monger.core.alpha :as monger]))

(derive ::connection ::monger/connection)
(derive ::uri-connection ::monger/uri-connection)


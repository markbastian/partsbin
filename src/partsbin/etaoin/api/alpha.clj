(ns partsbin.etaoin.api.alpha
  (:require [integrant.core :as ig]
            [etaoin.api :as etaoin]))

(derive ::firefox ::webdriver)
(derive ::chrome ::webdriver)

(defmethod ig/init-key ::firefox [_ config]
  (etaoin/firefox config))

(defmethod ig/init-key ::chrome [_ config]
  (etaoin/chrome config))

(defmethod ig/halt-key! ::webdriver [_ config]
  (etaoin/quit config))

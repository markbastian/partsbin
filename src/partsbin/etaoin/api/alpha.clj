(ns partsbin.etaoin.api.alpha
  (:require [integrant.core :as ig]
            [etaoin.api :as etaoin]
            [taoensso.timbre :as timbre]))

(derive ::firefox ::webdriver)
(derive ::chrome ::webdriver)

(defmethod ig/init-key ::firefox [_ config]
  (timbre/debug "Launching Firefox web driver.")
  (etaoin/firefox config))

(defmethod ig/init-key ::chrome [_ config]
  (timbre/debug "Launching Chrome web driver.")
  (etaoin/chrome config))

(defmethod ig/halt-key! ::webdriver [_ config]
  (timbre/debug "Quitting web driver.")
  (etaoin/quit config))

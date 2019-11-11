(ns partsbin.crux.api.alpha
  (:require [taoensso.timbre :as timbre]
            [crux.api :as crux]
            [integrant.core :as ig])
  (:import (crux.api ICruxAPI)))

(defmethod ig/init-key ::node [_ config]
  (timbre/debug "Starting crux node.")
  ^ICruxAPI (crux/start-node config))

(defmethod ig/halt-key! ::node [_ ^ICruxAPI node]
  (timbre/debug "Stopping crux node")
  (.close node))
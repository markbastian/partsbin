(ns partsbin.datascript.core.alpha
  (:require [datascript.core :as d]
            [integrant.core :as ig]
            [taoensso.timbre :as timbre]))

(defmethod ig/init-key ::connection [_ m]
  (timbre/debug "Creating Datascript DB connection.")
  (d/create-conn m))
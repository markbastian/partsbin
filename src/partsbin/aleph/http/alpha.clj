(ns partsbin.aleph.http.alpha
  (:require [aleph.http :as http]
            [integrant.core :as ig]
            [partsbin.middleware :refer [wrap-component]]
            [taoensso.timbre :as timbre])
  (:import (java.lang AutoCloseable)))

(defmethod ig/init-key ::server [_ {:keys [handler] :as m}]
  (timbre/debug "Launching Aleph web server.")
  (http/start-server (wrap-component handler m) m))

(defmethod ig/halt-key! ::server [_ ^AutoCloseable server]
  (timbre/debug "Stopping Aleph web server.")
  (.close server))

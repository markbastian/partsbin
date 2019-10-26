(ns partsbin.ring.adapter.jetty.alpha
  (:require [ring.adapter.jetty :as jetty]
            [integrant.core :as ig]
            [partsbin.middleware :refer [wrap-component]]
            [taoensso.timbre :as timbre])
  (:import [org.eclipse.jetty.server Server]))

(defmethod ig/init-key ::server [_ {:keys [handler] :as m}]
  (timbre/debug "Launching Jetty web server.")
  (jetty/run-jetty (wrap-component handler m) m))

(defmethod ig/halt-key! ::server [_ ^Server server]
  (timbre/debug "Stopping Jetty web server.")
  (.stop server))
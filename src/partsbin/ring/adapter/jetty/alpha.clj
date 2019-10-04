(ns partsbin.ring.adapter.jetty.alpha
  (:require [ring.adapter.jetty :as jetty]
            [integrant.core :as ig]
            [partsbin.middleware :refer [wrap-component]])
  (:import [org.eclipse.jetty.server Server]))

(defmethod ig/init-key ::server [_ {:keys [handler host port] :as m}]
  (jetty/run-jetty (wrap-component handler m) {:host host :port port}))

(defmethod ig/halt-key! ::server [_ ^Server server]
  (.stop server))
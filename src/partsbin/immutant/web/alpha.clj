(ns partsbin.immutant.web.alpha
  (:require [immutant.web :as immutant]
            [integrant.core :as ig]
            [partsbin.middleware :refer [wrap-component]]))

(defmethod ig/init-key ::server [_ {:keys [handler host port] :as m}]
  (immutant/run (wrap-component handler m) {:host host :port port}))

(defmethod ig/halt-key! ::server [_ server]
  (immutant/stop server))


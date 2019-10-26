(ns partsbin.immutant.web.v2
  (:require [immutant.web :as immutant]
            [integrant.core :as ig]
            [partsbin.middleware :refer [wrap-component]]
            [taoensso.timbre :as timbre]))

(def valid-options
  #{:path :dispatch? :trust-managers :key-managers :keystore :buffer-size :auto-start :buffers-per-region :static-dir
    :worker-threads :port :host :ssl-context :io-threads :client-auth :ajp-port :direct-buffers? :trust-password
    :virtual-host :key-password :truststore :configuration :contexts :http2? :servlet-name :filter-map :ssl-port})

(defmethod ig/init-key ::server [_ {:keys [handler] :as m}]
  (timbre/debug "Launching Immutant web server.")
  (immutant/run (wrap-component handler m) (select-keys m valid-options)))

(defmethod ig/halt-key! ::server [_ server]
  (timbre/debug "Stopping Immutant web server.")
  (immutant/stop server))

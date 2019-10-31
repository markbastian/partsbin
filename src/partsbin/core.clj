(ns partsbin.core
  "A variant of the reloaded system idea. However, in this case the system is captured in an atom instead of a dynamic
  var. This makes it more flexible and easy to have multiple independent systems if needed."
  (:require [integrant.core :as ig]))

(defprotocol IGSys
  (system [this])
  (config [this])
  (start [this])
  (stop [this])
  (restart [this])
  (restart-with [this new-config])
  (swap-config! [this f])
  (reset-config! [this new-config]))

(defn create [cfg]
  (let [config (atom cfg)
        state (atom nil)]
    (reify IGSys
      (system [this] @state)

      (config [this] @config)

      (start [this]
        (if @state
          @state
          (swap! state (fn [_] (ig/init @config))))
        this)

      (stop [this]
        (when @state
          (swap! state ig/halt!))
        this)

      (restart [this]
        (doto this stop start))

      (restart-with [this new-config]
        (reset! config new-config)
        (restart this))

      (swap-config! [this f]
        (do
          (stop this)
          (swap! config f)))

      (reset-config! [this new-config]
        (do
          (stop this)
          (reset! config new-config))))))
(ns partsbin.system
  "A variant of the reloaded system idea. However, in this case the system is captured in an atom instead of a dynamic
  var. This makes it more flexible and easy to have multiple independent systems if needed."
  (:require [integrant.core :as ig]))

(defprotocol IGSys
  (system [this])
  (start [this])
  (stop [this])
  (restart [this]))

(defn create [config]
  (let [state (atom nil)]
    (reify IGSys
      (system [this] @state)
      (start [this] (if @state @state (swap! state (fn [_] (ig/init config)))))
      (stop [this] (when @state (swap! state ig/halt!)))
      (restart [this] (do
                        (stop this)
                        (start this))))))
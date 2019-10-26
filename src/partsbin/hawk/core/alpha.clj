(ns partsbin.hawk.core.alpha
  (:require [integrant.core :as ig]
            [hawk.core :as hawk]
            [taoensso.timbre :as timbre]))

(defn wrap-handler [handler component]
  (let [component-ctx (dissoc component :opts :groups)]
    (fn [ctx event]
      (if (or (nil? ctx) (map? ctx))
        (handler (into component-ctx ctx) event)
        (handler ctx event)))))

(defn wrap-watch [{:keys [handler filter] :as m} component]
  (cond-> m
          handler (assoc :handler (wrap-handler handler component))
          filter (assoc :filter (wrap-handler filter component))))

(defmethod ig/init-key ::watch [_ {:keys [paths opts groups] :or {opts {}} :as component}]
  (timbre/debug (format "Launching file system watch on paths: %s" paths))
  (hawk/watch! opts (map #(wrap-watch % component) groups)))

(defmethod ig/halt-key! ::watch [_ watcher]
  (timbre/debug "Stopping file system watch.")
  (hawk/stop! watcher))
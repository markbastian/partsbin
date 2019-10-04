(ns partsbin.hawk.core.alpha
  (:require [integrant.core :as ig]
            [hawk.core :as hawk]))

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

(defmethod ig/init-key ::watch [_ {:keys [opts groups] :or {opts {}} :as component}]
  (hawk/watch! opts (map #(wrap-watch % component) groups)))

(defmethod ig/halt-key! ::watch [_ watcher]
  (hawk/stop! watcher))
(ns partsbin.monger.core.alpha
  (:require [monger.core :as mg]
            [integrant.core :as ig]))

;TODO - Test and verify
(defmethod ig/init-key ::connection [_ spec]
  (mg/connect spec))

(defmethod ig/halt-key! ::connection [_ connection]
  (mg/disconnect connection))

;TODO - Test and verify
(defmethod ig/init-key ::uri-connection [_ {:keys [uri]}]
  (let [{:keys [db conn]} (mg/connect-via-uri uri)]
    conn))

(defmethod ig/halt-key! ::uri-connection [_ connection]
  (mg/disconnect connection))

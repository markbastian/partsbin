(ns partsbin.durable-queue.alpha
  (:require [durable-queue :as dq]
            [integrant.core :as ig]))

(defmethod ig/init-key ::queue [_ {:keys [delete-on-halt? directory config]}]
  (cond-> (dq/queues directory config)
          delete-on-halt?
          (with-meta {:delete-on-halt? true})))

(defmethod ig/halt-key! ::queue [_ queue]
  (when (:delete-on-halt? (meta queue)) (dq/delete! queue)))
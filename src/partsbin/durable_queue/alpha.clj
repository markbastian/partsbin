(ns partsbin.durable-queue.alpha
  (:require [durable-queue :refer :all]
            [integrant.core :as ig]))

(defmethod ig/init-key ::queues [_ {:keys [delete-on-halt? directory config]}]
  (cond-> (queues directory config)
          delete-on-halt?
          (with-meta {:delete-on-halt? true})))

(defmethod ig/halt-key! ::queues [_ queue]
  (when (:delete-on-halt? (meta queue)) (delete! queue)))
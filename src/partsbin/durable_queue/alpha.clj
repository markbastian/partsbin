(ns partsbin.durable-queue.alpha
  (:require [durable-queue ::as dq]
            [integrant.core :as ig]))

(defmethod ig/init-key ::queue [_ {:keys [directory config]}]
  (dq/queues directory config))
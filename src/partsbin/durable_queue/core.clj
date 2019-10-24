(ns partsbin.durable-queue.core
  (:require [partsbin.durable-queue.alpha :as dq]))

(derive ::queue ::dq/queue)

(def config
  {::queue {:directory "/tmp"}})

(comment
  (def sys (ig/init config))
  (ig/halt! sys))

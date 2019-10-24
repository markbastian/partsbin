(ns partsbin.durable-queue.core
  (:require [partsbin.durable-queue.alpha :as durable]
            [durable-queue :refer :all]
            [integrant.core :as ig]))

(derive ::queues ::durable/queues)

(def config
  {::queues {:delete-on-halt? true
             :directory       "/tmp"}})

(comment
  (def sys (ig/init config))
  (def q (::queues sys))
  (take! q :my-queue 10 :timed-out!)
  (put! q :my-queue "a task")
  (def task (take! q :my-queue))
  @task
  (complete! task)
  (stats q)
  (ig/halt! sys))

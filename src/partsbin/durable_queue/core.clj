(ns partsbin.durable-queue.core
  (:require [partsbin.durable-queue.alpha :as dq]
            [durable-queue :refer :all]
            [integrant.core :as ig]))

(derive ::queue ::dq/queue)

(def config
  {::queue {:delete-on-halt? true
            :directory "/tmp"}})

(comment
  (def sys (ig/init config))
  (def q (::queue sys))
  (take! q :my-queue 10 :timed-out!)
  (put! q :my-queue "a task")
  (def task (take! q :my-queue))
  @task
  (complete! task)
  (stats q)
  (ig/halt! sys))

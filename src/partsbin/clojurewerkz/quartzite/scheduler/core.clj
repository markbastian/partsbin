(ns partsbin.clojurewerkz.quartzite.scheduler.core
  (:require [integrant.core :as ig]
            [partsbin.clojurewerkz.quartzite.scheduler.alpha :as quartzite]
            [clojurewerkz.quartzite.jobs :refer [defjob]]
            [clojurewerkz.quartzite.jobs :as j]
            [clojure.pprint :as pp]
            [clojurewerkz.quartzite.triggers :as t]
            [clojurewerkz.quartzite.schedule.simple :refer [schedule with-repeat-count with-interval-in-milliseconds]])
  (:import (java.util Date)
           (org.quartz Job)))

(derive ::scheduler ::quartzite/scheduler)

(defjob PrintTimeJob [ctx]
        (pp/pprint
          {:time (Date.)
           :ctx  ctx}))

(defrecord PrintTimeJobRecord []
  Job
  (execute [this ctx]
    (pp/pprint
      {:time (Date.)
       :foo (.get ctx "ABC")
       :m (.getMergedJobDataMap ctx)
       :ctx  ctx})))

(def job (j/build
           (j/of-type PrintTimeJobRecord)
           (j/with-identity (j/key "jobs.noop.1"))))

(def trigger (t/build
               (t/with-identity (t/key "triggers.1"))
               (t/start-now)
               #_
               (t/with-schedule (schedule
                                  (with-repeat-count 1)
                                  (with-interval-in-milliseconds 1000)))))
(def config
  {::quartzite/scheduler {:name "Quartzite"
                          :jobs [{:job job :trigger trigger}]}})

(comment
  (def sys (ig/init config))
  (ig/halt! sys))
(ns partsbin.immutant.scheduling.core
  (:require [integrant.core :as ig]
            [partsbin.immutant.scheduling.alpha :as scheduling]
            [clj-time.core :as t]
            [clj-time.periodic :refer [periodic-seq]])
  (:import (java.util Date)))

(derive ::job ::scheduling/job)

(defn job [{:keys [job-name] :as context}]
  (prn (str job-name ": " (Date.))))

(def config
  {[::job1 ::job] {:job-name "Job 1"
                   :job      job
                   :schedule {:in [5 :seconds] :every :second :limit 3}}
   [::job2 ::job] {:job-name "Job 2"
                   :job      job
                   :schedule {:cron "0 0 10 ? * MON-FRI"}}
   [::job3 ::job] {:job-name        "Job 3"
                   :job             job
                   :schedule-seq-fn (fn [] (periodic-seq (t/now) (t/seconds 1)))}})

(comment
  (def sys (ig/init config))
  (ig/halt! sys))

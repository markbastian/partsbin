(ns partsbin.immutant.scheduling.alpha
  (:require [immutant.scheduling :as sched]
            [immutant.scheduling.joda :as schedj]
            [integrant.core :as ig]
            [taoensso.timbre :as timbre]))

(defmethod ig/init-key ::job [_ {:keys [job schedule schedule-seq-fn] :as config}]
  (timbre/debug "Launching Immutant scheduling.")
  (let [job (fn [] (job config))]
    (cond
      schedule (sched/schedule job schedule)
      schedule-seq-fn (schedj/schedule-seq job (take 10 (schedule-seq-fn))))))

(defmethod ig/halt-key! ::job [_ job]
  (timbre/debug "Stopping Immutant scheduling.")
  (sched/stop job))

(comment
  (defn job []
    (prn 'fire!))

  (schedule
    job
    (-> (in 5 :seconds)
        (every :second)
        (limit 10)))
  (cron "0 0 10 ? * MON-FRI")
  (schedule job {:in [5 :seconds] :every :second :limit 3})
  )
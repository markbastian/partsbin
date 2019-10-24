(ns partsbin.clojurewerkz.quartzite.scheduler.alpha
  (:require [integrant.core :as ig]
            [clojurewerkz.quartzite.scheduler :as qs]
            [clojure.pprint :as pp])
  (:import (java.util Map)
           (org.quartz Scheduler)))

(defmethod ig/init-key ::scheduler [_ {:keys [jobs] :as opts}]
  (let [^Scheduler scheduler (qs/initialize)
        ^Scheduler scheduler (qs/start scheduler)
        ^Map context (.getContext scheduler)]
    (doseq [[k v] opts] (.put context (name k) v))
    (.put context "ABC" 123)
    (doseq [{:keys [job trigger]} jobs]
      (qs/schedule scheduler job trigger))
    (pp/pprint (bean scheduler))
    scheduler))

(defmethod ig/halt-key! ::scheduler [_ ^Scheduler scheduler]
  (qs/shutdown scheduler))
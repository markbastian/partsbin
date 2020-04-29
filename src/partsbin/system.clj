(ns partsbin.system
  "Utility ns for creating a ns-global reloadable system"
  (:require [integrant.core :as ig]))

(defmacro create-system
  "Create a ns-global system. Takes code that evaluates to a system configuration.
  The following items will be created:
    * *system* dynamic variable to hold the system.
    * 'system' function for viewing the current value of the system.
    * Functions start, stop, and restart which will do those actions on the system."
  [config]
  `(do
     (defonce ~(with-meta '*system* {:dynamic true}) nil)

     (defn ~'system [] ~'*system*)

     (defn ~'start []
       (alter-var-root ~'#'*system* (fn [~'s] (if-not ~'s (ig/init ~config) ~'s))))

     (defn ~'stop []
       (alter-var-root ~'#'*system* (fn [~'s] (when ~'s (do (ig/halt! ~'s) nil)))))

     (defn ~'restart [] (do (~'stop) (~'start)))))

(defmacro with-system [[bindings config] & body]
  `(let [system# (ig/init ~config)
         ~bindings system#]
     (try
       ~@body
       (finally (ig/halt! system#)))))
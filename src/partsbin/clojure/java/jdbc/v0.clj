(ns partsbin.clojure.java.jdbc.v0
  (:require [integrant.core :as ig]
            [clojure.java.jdbc :as j]
            [taoensso.timbre :as timbre])
  (:import (java.sql Connection)))

(defmethod ig/init-key ::connection [_ spec]
  (timbre/debug "Establishing jdbc connection.")
  {:connection (j/get-connection spec)})

(defmethod ig/halt-key! ::connection [_ {:keys [^Connection connection]}]
  (timbre/debug "Closing jdbc connection.")
  (.close connection))

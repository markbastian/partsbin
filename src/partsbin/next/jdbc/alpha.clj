(ns partsbin.next.jdbc.alpha
  (:require [integrant.core :as ig]
            [next.jdbc :as jdbc]
            [taoensso.timbre :as timbre])
  (:import (java.sql Connection)))

(defmethod ig/init-key ::connection [_ opts]
  (timbre/debug "Getting jdbc connection.")
  (jdbc/get-connection opts))

(defmethod ig/halt-key! ::connection [_ ^Connection connection]
  (timbre/debug "Closing jdbc connection.")
  (.close connection))

(defmethod ig/init-key ::datasource [_ opts]
  (timbre/debug "Getting jdbc datasource.")
  (jdbc/get-datasource opts))

;Cleanup required?
;(defmethod ig/halt-key! ::datasource [_ datasource]
;  (jdbc/ datasource))
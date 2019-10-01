(ns partsbin.clojure.java.jdbc
  (:require [integrant.core :as ig]
            [clojure.java.jdbc :as j])
  (:import (java.sql Connection)))

(defmethod ig/init-key ::connection [_ spec]
  {:connection (j/get-connection spec)})

(defmethod ig/halt-key! ::connection [_ {:keys [^Connection connection]}]
  (.close connection))
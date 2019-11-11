(ns partsbin.jdbc.pool.c3p0.alpha
  "Note - alpha namespace is subject to change."
  (:require [integrant.core :as ig]
            [jdbc.pool.c3p0 :as pool]
            [taoensso.timbre :as timbre])
  (:import (java.io Closeable)))

(defmethod ig/init-key ::datasource [_ opts]
  (timbre/debug "Launching c3p0 connection pool.")
  (pool/make-datasource-spec opts))

(defmethod ig/halt-key! ::datasource [_ ^Closeable datasource]
  (timbre/debug "Closing c3p0 datasource.")
  (.close datasource))
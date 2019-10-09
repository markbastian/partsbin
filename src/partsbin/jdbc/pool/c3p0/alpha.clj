(ns partsbin.jdbc.pool.c3p0.alpha
  "Note - alpha namespace is subject to change."
  (:require [integrant.core :as ig]
            [jdbc.pool.c3p0 :as pool]))

(defmethod ig/init-key ::datasource [_ opts]
  (pool/make-datasource-spec opts))
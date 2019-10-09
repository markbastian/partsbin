(ns partsbin.hikari-cp.core.alpha
  "Note - alpha namespace is subject to change."
  (:require [integrant.core :as ig]
            [hikari-cp.core :as hcp]))

;TODO - Should this be keyed as :datasouce for clojure.java.jdbc?
(defmethod ig/init-key ::datasource [_ opts]
  (hcp/make-datasource opts))

(defmethod ig/halt-key! ::datasource [_ datasource]
  (hcp/close-datasource datasource))


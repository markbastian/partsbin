(ns partsbin.hikari-cp.core.alpha
  "Note - alpha namespace is subject to change."
  (:require [integrant.core :as ig]
            [hikari-cp.core :as hcp]
            [taoensso.timbre :as timbre]))

;TODO - Should this be keyed as :datasouce for clojure.java.jdbc?
(defmethod ig/init-key ::datasource [_ opts]
  (timbre/debug "Creating datasource.")
  (hcp/make-datasource opts))

(defmethod ig/halt-key! ::datasource [_ datasource]
  (timbre/debug "Closing datasource.")
  (hcp/close-datasource datasource))


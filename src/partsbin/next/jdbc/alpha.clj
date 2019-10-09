(ns partsbin.next.jdbc.alpha
  (:require [integrant.core :as ig]
            [next.jdbc :as jdbc]))

;TODO - grok this better before implementing

(defmethod ig/init-key ::connection [_ opts]
  (jdbc/get-connection opts))

;(defmethod ig/halt-key! ::connection [_ datasource]
;  (jdbc/ datasource))


(defmethod ig/init-key ::datasource [_ opts]
  (jdbc/get-datasource opts))

;(defmethod ig/halt-key! ::datasource [_ datasource]
;  (jdbc/ datasource))


;(def ds (jdbc/get-datasource {:dbtype "h2" :dbname "example"}))
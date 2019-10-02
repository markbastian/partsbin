(ns partsbin.datomic.api
  (:require [integrant.core :as ig]
            [datomic.api :as d]))

(defmethod ig/init-key ::database [_ {:keys [db-uri] :as m}]
  (assoc m :created? (d/create-database db-uri)))

(defmethod ig/halt-key! ::database [_ {:keys [db-uri delete?]}]
  (when delete?
    (d/delete-database db-uri)))

(defmethod ig/init-key ::connection [_ {:keys [db-uri]}]
  (d/connect db-uri))
(ns partsbin.datomic.api.alpha
  (:require [integrant.core :as ig]
            [datomic.api :as d]
            [taoensso.timbre :as timbre]))

(defmethod ig/init-key ::database [_ {:keys [db-uri] :as m}]
  (timbre/debug "Creating Datomic database.")
  (let [created (d/create-database db-uri)]
    (if created
      (timbre/debug "Created Datomic database.")
      (timbre/debug "Did not create Datomic database (Probably already exists)."))
    (assoc m :created? created)))

(defmethod ig/halt-key! ::database [_ {:keys [db-uri delete?]}]
  (when delete?
    (do
      (timbre/debug "Deleting Datomic database.")
      (d/delete-database db-uri))))

(defmethod ig/init-key ::connection [_ {:keys [db-uri]}]
  (timbre/debug "Connecting to Datomic database.")
  (d/connect db-uri))

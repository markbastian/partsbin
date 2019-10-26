(ns partsbin.monger.core.alpha
  (:require [monger.core :as mg]
            [integrant.core :as ig]
            [taoensso.timbre :as timbre]
            [clojure.pprint :as pp])
  (:import (com.mongodb.client MongoClient)))

(defmethod ig/init-key ::connection [_ spec]
  (timbre/debug (format "Initializing Mongo connection with params %s" spec))
  (mg/connect spec))

(defmethod ig/halt-key! ::connection [_ connection]
  (timbre/debug "Disconnecting from Mongo connection...")
  (mg/disconnect connection))

(defmethod ig/init-key ::database [_ {:keys [^MongoClient conn db-name]}]
  (timbre/debug (format "Getting Mongo DB: %s" db-name))
  (mg/get-db conn db-name))

;TODO - Determine:
; 1. Do we need to disconnect from a uri connection?
; 2. The correct thing to return. The conn, the map, etc.
;(defmethod ig/init-key ::uri-connection [_ {:keys [uri]}]
;  (let [{:keys [db conn]} (mg/connect-via-uri uri)]
;    conn))
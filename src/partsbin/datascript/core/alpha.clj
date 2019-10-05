(ns partsbin.datascript.core.alpha
  (:require [datascript.core :as d]
            [integrant.core :as ig]))

(defmethod ig/init-key ::connection [_ m]
  (d/create-conn m))
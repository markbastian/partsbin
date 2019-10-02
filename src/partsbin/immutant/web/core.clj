(ns partsbin.immutant.web.core
  (:require [partsbin.immutant.web.v2 :as web]
            [integrant.core :as ig]))

(derive ::server ::web/server)


(def config {::server {:host    "0.0.0.0"
                       :port    3000
                       :handler (constantly {:status 200 :body "OK"})}})

(comment
  (def system (ig/init config)))